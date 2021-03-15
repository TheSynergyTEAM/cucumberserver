package cucumbermarket.cucumbermarketspring.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class ChatRoom {

    @Id
    @GeneratedValue
    @Column(name = "chatRoom_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatMessages> messagesList;

    private LocalDateTime createdDate;

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public List<ChatMessages> getMessagesList() {
        return messagesList;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}
