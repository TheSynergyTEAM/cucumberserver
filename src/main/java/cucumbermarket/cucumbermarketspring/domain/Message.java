package cucumbermarket.cucumbermarketspring.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
public class Message {

    @Id
    @GeneratedValue
    @Column(name = "message_id")
    private Long id;

    private String content;
    private LocalDateTime createdDate;

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}
