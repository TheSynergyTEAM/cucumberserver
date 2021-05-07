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
        var chatId = String.format("%s_%s_%s", senderId, receiverId, itemId);
        ChatRoom chatRoomBySender = ChatRoom
                .builder()
                .chatId(chatId)
                .senderId(senderId)
                .receiverId(receiverId)
                .build();

        ChatRoom chatRoomByReceiver = ChatRoom
                .builder()
                .chatId(chatId)
                .senderId(receiverId)
                .receiverId(senderId)
                .build();
        chatRoomRepository.save(chatRoomBySender);
        chatRoomRepository.save(chatRoomByReceiver);
        return chatId;
    }

    @Transactional(readOnly = true)
    public ChatRoom searchChatRoom(Long chatRoomId) {
        return chatRoomRepository.getOne(chatRoomId);
    }

    @Transactional(readOnly = true)
    public String getChatId(Long senderId, Long receiverId) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        return chatRoom.get().getChatId();
    }


    @Transactional
    public Optional<String> getChatId(Long senderId, Long receiverId, Long itemId, Boolean createIfNotExist) {
        try {
            Optional<ChatRoom> bySenderIdAndReceiverId = chatRoomRepository.findBySenderIdAndReceiverId(senderId, receiverId);
            ChatRoom chatRoom = bySenderIdAndReceiverId.get();
            return Optional.ofNullable(chatRoom.getChatId());

        } catch (EntityNotFoundException e) {
            return Optional.ofNullable(createChatRoom(senderId, receiverId, itemId));
        }

    }
//    @Transactional(readOnly = true)
//    public ChatRoom searchChatRoomByMemberAndItem(Member member, Item item) {
//        return chatRoomRepository.findByMemberAndItem(member, item);
//    }

//    @Transactional
//    public void addMessage(Member member, Item item, Message message) {
//        ChatRoom byMemberAndItem = chatRoomRepository.findByMemberAndItem(member, item);
//        byMemberAndItem.addMessage(message);
//    }

//    @Transactional
//    public List<Message> getAllMessage(Member member, Item item) {
//        ChatRoom chatRoom = chatRoomRepository.findByMemberAndItem(member, item);
//        return chatRoom.getMessageList();
//    }

//    @Transactional
//    public List<ChatRoom> getAllChatRoom(Item item) {
//        return chatRoomRepository.findAllByItem(item);
//    }
}