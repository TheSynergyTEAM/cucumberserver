package cucumbermarket.cucumbermarketspring.domain.chat.chatroom;

import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>{

    //    ChatRoom findByMemberAndItem(Member member, Item item);
//
//    List<ChatRoom> findAllByItem(Item item);
    Optional<ChatRoom> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
