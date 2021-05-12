package cucumbermarket.cucumbermarketspring.domain.chat.Message.service;

import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.MessageRepository;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.MessageStatus;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.service.ChatRoomService;
import cucumbermarket.cucumbermarketspring.domain.chat.socket.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRoomService chatRoomService;

    /**
     * 메세지 생성
     */
    @Transactional
    public Message createMessage(MessageDto messageDto) {
        Message originMessage = Message.builder()
                .senderId(messageDto.getSenderId())
                .receiverId(messageDto.getReceiverId())
                .chatId(String.format("%s_%s_%s", messageDto.getSenderId(), messageDto.getReceiverId(), messageDto.getItemId()))
                .content(messageDto.getContent())
                .messageStatus(MessageStatus.RECEIVED)
                .build();
        messageRepository.save(originMessage);
        Message createdMessage = Message.builder()
                .senderId(messageDto.getSenderId())
                .receiverId(messageDto.getReceiverId())
                .chatId(String.format("%s_%s_%s", messageDto.getReceiverId(), messageDto.getSenderId(), messageDto.getItemId()))
                .content(messageDto.getContent())
                .messageStatus(MessageStatus.RECEIVED)
                .build();
        messageRepository.save(createdMessage);
        return createdMessage;
    }

    /**
     * 메세지 조회
     */
    @Transactional
    public List<Message> findMessages(Long senderId, Long receiverId, Long itemId) {
        // TODO Exception Handling
        Optional<String> chatId = chatRoomService.getChatId(senderId, receiverId, itemId);
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
    public Integer countNewMessages(Long senderId, Long receiverId, Long itemId) {
        Optional<String> chatId = chatRoomService.getChatId(senderId, receiverId, itemId);
        return messageRepository.findByChatIdAndMessageStatus(chatId, String.valueOf(MessageStatus.RECEIVED));
    }


}
