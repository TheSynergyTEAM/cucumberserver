package cucumbermarket.cucumbermarketspring.domain.chat.Message.service;

import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.MessageRepository;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.MessageStatus;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.service.ChatRoomService;
import cucumbermarket.cucumbermarketspring.domain.chat.socket.dto.MessageDto;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    private final MessageRepository messageRepository;
    private final ChatRoomService chatRoomService;
    private final MemberService memberService;
    /**
     * 메세지 생성
     */
    @Transactional
    public void createMessage(@Payload MessageDto messageDto) {

        Message originMessage = Message.builder()
                .senderId(messageDto.getSenderId())
                .receiverId(messageDto.getReceiverId())
                .chatId(String.format("%s_%s_%s", messageDto.getSenderId(), messageDto.getReceiverId(), messageDto.getItemId()))
                .messageStatus(MessageStatus.RECEIVED)
                .content(messageDto.getContent())
                .build();
        messageRepository.save(originMessage);
        Message createdMessage = Message.builder()
                .senderId(messageDto.getSenderId())
                .receiverId(messageDto.getReceiverId())
                .chatId(String.format("%s_%s_%s", messageDto.getReceiverId(), messageDto.getSenderId(), messageDto.getItemId()))
                .messageStatus(MessageStatus.RECEIVED)
                .content(messageDto.getContent())
                .build();
        messageRepository.save(createdMessage);
        Member sender = memberService.searchMemberById(originMessage.getSenderId());
        Member receiver = memberService.searchMemberById(originMessage.getReceiverId());
        String destination1 = "/user/" + sender.getId() + "/" + receiver.getId() + "/" + messageDto.getItemId() + "/queue/messages";
        String destination2 = "/user/" + receiver.getId() + "/" + sender.getId() + "/" + messageDto.getItemId() + "/queue/messages";
        simpMessagingTemplate.convertAndSend(
                destination1,
                originMessage
        );

        simpMessagingTemplate.convertAndSend(
                destination2,
                originMessage

        );
//        simpMessagingTemplate.convertAndSendToUser(
//                String.valueOf(sender.getId()),
//                receiver.getId() + "/" + messageDto.getItemId() + "/queue/messages",
//                originMessage,
//                createMessageHeaders(String.valueOf(sender.getId()))
//        );
//        simpMessagingTemplate.convertAndSendToUser(
//                String.valueOf(receiver.getId()),
//                "/"+sender.getId() + "/" + messageDto.getItemId() + "/queue/messages",
//                originMessage,
//                createMessageHeaders(String.valueOf(receiver.getId()))
//        );
    }

    /**
     * 메세지 조회
     */
    @Transactional
    public List<Message> findMessages(Long senderId, Long receiverId, Long itemId) {
        // TODO Exception Handling
        Optional<String> chatId = getChatId(senderId, receiverId, itemId);
        return messageRepository.findByChatId(chatId);
    }

    /**
     * 메세지 삭제
     */
    @Transactional
    public void deleteMessage(Long messageId) {
        messageRepository.deleteById(messageId);
    }

    /**
     * 메세지 수 조회 (읽지 않은 메세지)
     */
    @Transactional
    public Long countNewMessages(Long senderId, Long receiverId, Long itemId) {
        Optional<String> chatId = getChatId(senderId, receiverId, itemId);
        List<Message> byChatId = messageRepository.findByChatId(chatId);
        return byChatId.stream().filter(message -> message.getMessageStatus().equals(MessageStatus.RECEIVED)).count();
    }


    @Transactional
    public void updateMessages(Long senderId, Long receiverId, Long itemId) {
        Optional<String> chatId = getChatId(senderId, receiverId, itemId);
        List<Message> allMessages = messageRepository.findByChatId(chatId);
        for (Message message : allMessages) {
            if (message.getMessageStatus().equals(MessageStatus.RECEIVED)) {
                message.updateStatus();
            }
        }
        return;
    }

    private Optional<String> getChatId(Long senderId, Long receiverId, Long itemId) {
        return chatRoomService.getChatId(senderId, receiverId, itemId);
    }

    private MessageHeaders createMessageHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor
                .create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }

}
