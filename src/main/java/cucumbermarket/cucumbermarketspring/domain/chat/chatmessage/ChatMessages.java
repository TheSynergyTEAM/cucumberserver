package cucumbermarket.cucumbermarketspring.domain.chat.chatmessage;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoom;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
import lombok.Getter;

import javax.persistence.*;

@Getter
//@NoArgsConstructor
@Entity
@Table(name = "chat_messages")
public class ChatMessages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatMessage_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

    @ManyToOne
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatroom;
}
