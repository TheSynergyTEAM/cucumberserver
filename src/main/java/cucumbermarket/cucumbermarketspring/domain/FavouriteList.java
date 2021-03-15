package cucumbermarket.cucumbermarketspring.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class FavouriteList {

    @Id
    @GeneratedValue
    @Column(name = "favlist_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "favouriteList")
    private List<FavouriteItem> favouriteItemList;

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public List<FavouriteItem> getFavouriteItemList() {
        return favouriteItemList;
    }
}
