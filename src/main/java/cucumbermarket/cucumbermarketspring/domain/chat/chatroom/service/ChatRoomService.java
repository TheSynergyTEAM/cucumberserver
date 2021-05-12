package cucumbermarket.cucumbermarketspring.domain.chat.chatroom.service;

import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoom;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoomRepository;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.item.ItemRepository;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    /**
     * 채팅방 생성
     */
    @Transactional
    public String createChatRoom(Long senderId, Long receiverId, Long itemId) {
        var chatId1 = String.format("%s_%s_%s", senderId, receiverId, itemId);
        var chatId2 = String.format("%s_%s_%s", receiverId, senderId, itemId);
        ChatRoom chatRoomBySender = ChatRoom
                .builder()
                .chatId(chatId1)
                .senderId(senderId)
                .receiverId(receiverId)
                .build();

        ChatRoom chatRoomByReceiver = ChatRoom
                .builder()
                .chatId(chatId2)
                .senderId(receiverId)
                .receiverId(senderId)
                .build();
        chatRoomRepository.save(chatRoomBySender);
        chatRoomRepository.save(chatRoomByReceiver);
        return chatId1;
    }

    @Transactional(readOnly = true)
    public ChatRoom searchChatRoom(Long chatRoomId) {
        return chatRoomRepository.getOne(chatRoomId);
    }


    @Transactional
    public Optional<String> getChatId(Long senderId, Long receiverId, Long itemId) {
        try {
            String chatId = String.format("%s_%s_%s", senderId, receiverId, itemId);
            Optional<ChatRoom> bySenderIdAndReceiverId = chatRoomRepository.findByChatId(chatId);
            ChatRoom chatRoom = bySenderIdAndReceiverId.get();
            return Optional.ofNullable(chatRoom.getChatId());

        } catch (NoSuchElementException e) {
            return Optional.ofNullable(createChatRoom(senderId, receiverId, itemId));
        }

    }

}