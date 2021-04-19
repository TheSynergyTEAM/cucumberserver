package cucumbermarket.cucumbermarketspring.domain.chat.chatroom.service;

import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoom;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoomRepository;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    /**
     * 채팅방 생성
     */
    @Transactional
    public Long createChatRoom(ChatRoom chatRoom) {
        ChatRoom saved = chatRoomRepository.save(chatRoom);
        return saved.getId();
    }

    @Transactional(readOnly = true)
    public ChatRoom searchChatRoom(Long chatRoomId) {
        return chatRoomRepository.getOne(chatRoomId);
    }

    @Transactional(readOnly = true)
    public ChatRoom searchChatRoomByMemberAndItem(Member member, Item item) {
        return chatRoomRepository.findByMemberAndItem(member, item);
    }

    @Transactional
    public void addMessage(Member member, Item item, Message message) {
        ChatRoom byMemberAndItem = chatRoomRepository.findByMemberAndItem(member, item);
        byMemberAndItem.addMessage(message);
    }

    @Transactional
    public List<Message> getAllMessage(Member member, Item item) {
        ChatRoom chatRoom = chatRoomRepository.findByMemberAndItem(member, item);
        return chatRoom.getMessageList();
    }

    @Transactional
    public List<ChatRoom> getAllChatRoom(Item item) {
        return chatRoomRepository.findAllByItem(item);
    }
}