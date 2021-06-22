package cucumbermarket.cucumbermarketspring.domain.chat.Message;
import cucumbermarket.cucumbermarketspring.domain.BaseTimeEntity;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoom;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name = "message")
public class Message extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @Column(name = "chat_id")
    private String chatId;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

    public void updateStatus() {
        this.messageStatus = MessageStatus.READ;
    }
}
