package cucumbermarket.cucumbermarketspring.domain.chat.chatroom;
import cucumbermarket.cucumbermarketspring.domain.BaseTimeEntity;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.item.Item;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "chatroom")
@Builder
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    @Column(name = "chat_id")
    private String chatId;

//    @ManyToOne(targetEntity = Item.class, fetch = FetchType.LAZY)
//    @JoinColumn(name = "item_id")
//    private Item item;
//
//    /**
//     * 채팅 방을 만든 사용자 (연락을 시도한 유저)
//     */
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "receiver_id")
    private Long receiverId;

//    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.PERSIST)
//    private List<Message> messageList = new ArrayList<>();

//    @Builder
//    public ChatRoom(Long itemOwnerId, Long itemBuyerId) {
//        this.itemOwnerId = itemOwnerId;
//        this.itemBuyerId = itemBuyerId;
//    }

//    public void addMessage(Message message) {
//        this.messageList.add(message);
//    }


}
