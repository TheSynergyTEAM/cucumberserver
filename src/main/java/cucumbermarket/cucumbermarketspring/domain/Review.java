package cucumbermarket.cucumbermarketspring.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime registerDate;
    private String content;
    private int ratings;

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public Member getMember() {
        return member;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public String getContent() {
        return content;
    }

    public int getRatings() {
        return ratings;
    }
}
