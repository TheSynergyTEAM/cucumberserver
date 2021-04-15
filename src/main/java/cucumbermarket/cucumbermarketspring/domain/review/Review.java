package cucumbermarket.cucumbermarketspring.domain.review;

import com.fasterxml.jackson.annotation.JsonBackReference;
import cucumbermarket.cucumbermarketspring.domain.BaseTimeEntity;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
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
    /**
     * 칼럼
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Member.class)
    @JoinColumn(name = "member_id", updatable = false)
    @JsonBackReference
    private Member member;  // 구매자

    private int ratingScore;

    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * 빌더
     * */
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