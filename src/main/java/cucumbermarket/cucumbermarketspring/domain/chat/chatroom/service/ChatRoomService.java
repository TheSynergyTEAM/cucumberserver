package cucumbermarket.cucumbermarketspring.domain.chat.chatroom.service;

import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoom;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoomRepository;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoomSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

//    @Transactional(readOnly = true)
//    public ChatRoom searchChatRoomByName(ChatRoomSearchDTO chatRoomSearchDTO) {
//        ChatRoom byDTO = chatRoomRepository.findByDTO(chatRoomSearchDTO);
//        return byDTO;
//    }
}