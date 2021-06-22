package cucumbermarket.cucumbermarketspring.domain.chat.Message.service;

import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.MessageRepository;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.MessageStatus;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoom;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.service.ChatRoomService;
import cucumbermarket.cucumbermarketspring.domain.chat.socket.dto.ChatRoomMessagesDTO;
import cucumbermarket.cucumbermarketspring.domain.chat.socket.dto.MessageDto;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Long senderId = messageDto.getSenderId();
        Long receiverId = messageDto.getReceiverId();
        Long itemId = messageDto.getItemId();
        String chatId = getChatId(senderId, receiverId, itemId).get();
        String chatId2 = getChatId(receiverId, senderId, itemId).get();
        Message originMessage = Message.builder()
                .senderId(messageDto.getSenderId())
                .receiverId(messageDto.getReceiverId())
                .chatId(chatId)
                .messageStatus(MessageStatus.READ)
                .content(messageDto.getContent())
                .build();
        messageRepository.save(originMessage);
        Message createdMessage = Message.builder()
                .senderId(messageDto.getReceiverId())
                .receiverId(messageDto.getSenderId())
                .chatId(chatId2)
                .messageStatus(MessageStatus.UNREAD)
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
    }

    /**
     * 메세지 조회
     */
    @Transactional
    public ChatRoomMessagesDTO findMessages(Long senderId, Long receiverId, Long itemId, int page) {
        // TODO Exception Handling
        Optional<String> chatId = getChatId(senderId, receiverId, itemId);
        Pageable pageable = PageRequest.of(page, 20, Sort.by("created").descending());
        Page<Message> messagePage;
        messagePage = messageRepository.findByChatId(chatId, pageable);
        List<Message> content = messagePage.getContent();
        ChatRoomMessagesDTO chatRoomMessagesDTO = new ChatRoomMessagesDTO(
                String.valueOf(messagePage.getNumber()),
                String.valueOf(messagePage.getTotalPages()),
                String.valueOf(messagePage.getTotalElements()),
                content
        );

        return chatRoomMessagesDTO;
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
//    @Transactional
//    public Long countNewMessages(Long senderId) {
////        Optional<String> chatId = getChatId(senderId, receiverId, itemId);
//        List<ChatRoom> chatRoomList = chatRoomService.getChatRoomListBySenderId(senderId);
//        for (ChatRoom chatRoom : chatRoomList) {
//            List<Message> messages = chatRoomService.allMessages(chatRoom.getChatId());
//
//        }
////        return byChatId.stream().filter(message -> message.getMessageStatus().equals(MessageStatus.RECEIVED)).count();
//    }


    @Transactional
    public void updateMessages(Long senderId, Long receiverId, Long itemId) {
        String chatId = getChatId(senderId, receiverId, itemId).get();
        List<Message> allMessages = allMessages(chatId);
        System.out.println("allMessages.size() = " + allMessages.size());
        for (Message message : allMessages) {
            if (message.getMessageStatus().equals(MessageStatus.UNREAD)) {
                message.updateStatus();
            }
        }
        return;
    }

    @Transactional
    public List<Message> allMessages(String chatId) {

        Pageable pageable = PageRequest.of(0, 1000);
        Page<Message> byChatId = messageRepository.findByChatId(Optional.ofNullable(chatId), pageable);
        return byChatId.getContent();
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
