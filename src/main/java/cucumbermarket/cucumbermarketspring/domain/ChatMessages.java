package cucumbermarket.cucumbermarketspring.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
public class ChatMessages {

    @Id
    @GeneratedValue
    @Column(name = "chat_message_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

    @ManyToOne
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom;

    public Long getId() {
        return id;
    }

    public Message getMessage() {
        return message;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }
}
