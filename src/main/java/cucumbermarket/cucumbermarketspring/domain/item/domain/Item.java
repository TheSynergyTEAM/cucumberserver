package cucumbermarket.cucumbermarketspring.domain.item.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import cucumbermarket.cucumbermarketspring.domain.BaseTimeEntity;
import cucumbermarket.cucumbermarketspring.domain.favourite.domain.FavouriteItem;
import cucumbermarket.cucumbermarketspring.domain.file.domain.File;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
import cucumbermarket.cucumbermarketspring.domain.review.domain.Review;
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, targetEntity = Member.class)
    @JoinColumn(name = "member_id", updatable = false)
    //@JsonIgnoreProperties(value = {"item"})
    @JsonBackReference
    private Member member;

    @Embedded
    private Address address;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private Categories categories;

    private int price;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String spec;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<File> photo = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FavouriteItem> favouriteItem = new ArrayList<>();

    private Boolean sold;

    @OneToOne(mappedBy = "item")
    private Review review;

    //빌더
    @Builder
    public Item(Member member, String title, Categories categories, int price, String spec, Address address, Boolean sold){
        this.member = member;
        this.title = title;
        this.categories = categories;
        this.price = price;
        this.spec = spec;
        this.address = address;
        this.sold = sold;
    }

    public void update(String title, Categories categories, int price, String spec, Address address, Boolean sold){
        this.title = title;
        this.address = address;
        this.categories = categories;
        this.price = price;
        this.spec = spec;
        this.sold = sold;
    }

}