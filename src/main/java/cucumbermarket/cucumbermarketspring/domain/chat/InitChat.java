package cucumbermarket.cucumbermarketspring.domain.chat;

import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.MessageRepository;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.MessageStatus;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.service.MessageService;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class InitChat {

    private final MessageRepository messageRepository;
    private final ChatRoomService chatRoomService;

    @Transactional
    public void init() {
        initChatRoomAndMessages(2L, 1L, 1L);
        initChatRoomAndMessages(3L, 1L, 1L);
        initChatRoomAndMessages(10L, 2L, 2L);
        initChatRoomAndMessages(14L, 2L, 2L);

    }

    private void initChatRoomAndMessages(Long senderId, Long receiverId, Long itemId) {
        String chatId1 = chatRoomService.getChatId(senderId, receiverId, itemId).get();
        String chatId2 = chatRoomService.getChatId(receiverId, senderId, itemId).get();

        for (int i = 0; i < 22; i++) {

            Message msg = getBuild(chatId1, i, senderId, receiverId, MessageStatus.READ);
            messageRepository.save(msg);
            Message msg2 = getBuild(chatId2, i, receiverId, senderId, MessageStatus.READ);
            messageRepository.save(msg2);

        }
        Message msg1 = getBuild(chatId1, 22, senderId, receiverId, MessageStatus.READ);
        messageRepository.save(msg1);
        Message msg2 = getBuild(chatId2, 22, receiverId, senderId, MessageStatus.UNREAD);
        messageRepository.save(msg2);
    }

    private Message getBuild(String chatId1, int i, long senderId, long receiverId, MessageStatus messageStatus) {
        return Message.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .chatId(chatId1)
                .messageStatus(messageStatus)
                .content("메세지" + (i + 1)).build();
    }

}
