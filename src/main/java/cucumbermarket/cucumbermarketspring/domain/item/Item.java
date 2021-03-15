package cucumbermarket.cucumbermarketspring.domain.item;
import cucumbermarket.cucumbermarketspring.domain.BaseTimeEntity;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.upload.photo.Photo;
import cucumbermarket.cucumbermarketspring.domain.review.Review;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "item")
public class Item extends BaseTimeEntity {
    //칼럼
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private Categories categories;

    private int price;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String spec;

    @OneToMany(mappedBy = "item")
    private List<Photo> photo = new ArrayList<>();

    private Boolean sold;

    @OneToOne(mappedBy = "item")
    private Review review;

    //빌더
    @Builder
    public Item(Member member, String title, Categories categories, int price, String spec){
        this.member = member;
        this.title = title;
        this.categories = categories;
        this.price = price;
        this.spec = spec;
    }
}
