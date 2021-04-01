package cucumbermarket.cucumbermarketspring.domain.chat.Message.service;

import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    /**
     * 메세지 생성
     */
    @Transactional
    public Long createMessage(Message message) {
        messageRepository.save(message);
        return message.getId();
    }

    /**
     * 메세지 조회
     */
    @Transactional
    public Message searchMessage(Long messageId) {
        return messageRepository.getOne(messageId);
    }

    /**
     * 메세지 삭제
     */
    @Transactional
    public void deleteMessage(Long messageId) {
        messageRepository.deleteById(messageId);
    }

}
