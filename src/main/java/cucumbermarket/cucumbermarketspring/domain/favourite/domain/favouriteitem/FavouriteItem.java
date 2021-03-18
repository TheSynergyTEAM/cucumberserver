package cucumbermarket.cucumbermarketspring.domain.favourite.domain.favouriteitem;

import cucumbermarket.cucumbermarketspring.domain.BaseTimeEntity;
import cucumbermarket.cucumbermarketspring.domain.favourite.domain.favouritelist.FavouriteList;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "favourite_item")
public class FavouriteItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favouriteItem_id")
    private Long id;

    @ManyToOne(targetEntity = Item.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(targetEntity = FavouriteList.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "favouriteList_id")
    private FavouriteList favouriteList;
}
