package cucumbermarket.cucumbermarketspring.domain.review.domain;

import cucumbermarket.cucumbermarketspring.domain.BaseTimeEntity;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "review")
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private int ratingScore;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder
    public Review(Item item, Member member, int ratingScore, String content){
        this.item = item;
        this.member = member;
        this.ratingScore = ratingScore;
        this.content = content;
    }

    public void update(String content, int ratingScore){
        this.content = content;
        this.ratingScore = ratingScore;
    }
}