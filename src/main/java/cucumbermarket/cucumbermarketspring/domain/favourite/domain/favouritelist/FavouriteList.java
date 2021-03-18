package cucumbermarket.cucumbermarketspring.domain.favourite.favouritelist;

import cucumbermarket.cucumbermarketspring.domain.favourite.domain.favouriteitem.FavouriteItem;
import cucumbermarket.cucumbermarketspring.domain.favourite.favouriteitem.FavouriteItem;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "favourite_list")
public class FavouriteList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favouriteList_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "favouriteList")
    @Column(name = "item_name")
    private List<FavouriteItem> itemList = new ArrayList<>();
}