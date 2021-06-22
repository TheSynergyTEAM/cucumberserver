package cucumbermarket.cucumbermarketspring.domain.chat;

import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.MessageRepository;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.MessageStatus;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.service.MessageService;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoomRepository;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.service.ChatRoomService;
import cucumbermarket.cucumbermarketspring.domain.chat.socket.dto.MessageDto;
import cucumbermarket.cucumbermarketspring.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class InitChat {

    private final MessageRepository messageRepository;
    private final MessageService messageService;
    private final ChatRoomService chatRoomService;

    @Transactional
    public void init() {
        String chatId1 = chatRoomService.getChatId(2L, 1L, 1L).get();
        String chatId2 = chatRoomService.getChatId(1L, 2L, 1L).get();

        for (int i = 0; i < 22; i++) {

            Message msg = getBuild(chatId1, i, 2L, 1L, MessageStatus.READ);
            messageRepository.save(msg);
            Message msg2 = getBuild(chatId2, i, 1L, 2L, MessageStatus.READ);
            messageRepository.save(msg2);

        }
        Message msg1 = getBuild(chatId1, 22, 2, 1, MessageStatus.READ);
        messageRepository.save(msg1);
        Message msg2 = getBuild(chatId2, 22, 1, 2, MessageStatus.UNREAD);
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
