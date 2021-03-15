package cucumbermarket.cucumbermarketspring.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class FavouriteItem {

    @Id
    @GeneratedValue
    @Column(name = "favitem_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "favlist_id")
    private FavouriteList favouriteList;

    private LocalDateTime createdDate;

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public FavouriteList getFavouriteList() {
        return favouriteList;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}
