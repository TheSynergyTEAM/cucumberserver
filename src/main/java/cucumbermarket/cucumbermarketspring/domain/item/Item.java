package cucumbermarket.cucumbermarketspring.domain.item;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import cucumbermarket.cucumbermarketspring.domain.BaseTimeEntity;
import cucumbermarket.cucumbermarketspring.domain.favourite.FavouriteItem;
import cucumbermarket.cucumbermarketspring.domain.file.Photo;
import cucumbermarket.cucumbermarketspring.domain.item.category.Categories;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
import cucumbermarket.cucumbermarketspring.domain.review.Review;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "item")
public class Item extends BaseTimeEntity {
    /**
     * 칼럼
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Member.class)
    @JoinColumn(name = "member_id", updatable = false)
    @JsonBackReference
    private Member member;  // 판매자

    @Embedded
    private Address address;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private Categories categories;

    private int price;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String spec;

    private Boolean sold;

    @Column(columnDefinition = "int default 0")
    private int views;

    @OneToMany(mappedBy = "item", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Photo> photo = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.MERGE, orphanRemoval = true)
    @JsonManagedReference("item")
    private List<FavouriteItem> favouriteItem = new ArrayList<>();

    @OneToOne(mappedBy = "item")
    private Review review;

    /**
     * 빌더
     * */
    @Builder
    public Item(Member member, String title, Categories categories, int price, String spec, Address address, Boolean sold, int views){
        this.member = member;
        this.title = title;
        this.categories = categories;
        this.price = price;
        this.spec = spec;
        this.address = address;
        this.sold = sold;
        this.views = views;
    }

    public void update(String title, Categories categories, int price, String spec, Address address, Boolean sold){
        this.title = title;
        this.address = address;
        this.categories = categories;
        this.price = price;
        this.spec = spec;
        this.sold = sold;
    }

    public void updateView(int views){
        this.views = views + 1;
    }

    public void addPhoto(Photo photo){
        this.photo.add(photo);

        if(photo.getItem() != this)
            photo.setItem(this);
    }
}