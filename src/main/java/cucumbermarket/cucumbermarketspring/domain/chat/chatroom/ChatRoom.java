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

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "receiver_id")
    private Long receiverId;

}
