package cucumbermarket.cucumbermarketspring.domain.chat.Message;
import cucumbermarket.cucumbermarketspring.domain.BaseTimeEntity;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.ChatRoom;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "message")
public class Message extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "chat_room")
    private ChatRoom chatRoom;

    @Builder
    public Message(Member member, String content)
    {
        this.content = content;
        this.member = member;
    }
}
