package cucumbermarket.cucumbermarketspring.domain.chat.chatroom;
import cucumbermarket.cucumbermarketspring.domain.BaseTimeEntity;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
<<<<<<< HEAD
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
=======
import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
>>>>>>> 542b833f949a4a026ad951075ec1e42e9cb04c76
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "chatroom")
public class ChatRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    @ManyToOne(targetEntity = Item.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

<<<<<<< HEAD
    /**
     * 채팅 방을 만든 사용자 (연락을 시도한 유저)
     */
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "chatRoom")
    private List<Message> messageList = new ArrayList<>();

    @Builder
    public ChatRoom(Item item, Member member) {
        this.item = item;
        this.member = member;
    }

=======
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.PERSIST)
    private List<Message> message = new ArrayList<>();
>>>>>>> 542b833f949a4a026ad951075ec1e42e9cb04c76
}
