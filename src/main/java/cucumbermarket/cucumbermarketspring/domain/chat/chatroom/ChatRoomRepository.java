package cucumbermarket.cucumbermarketspring.domain.chat.chatroom;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>{

//    public ChatRoom findByDTO(ChatRoomSearchDTO chatRoomSearchDTO);
}
