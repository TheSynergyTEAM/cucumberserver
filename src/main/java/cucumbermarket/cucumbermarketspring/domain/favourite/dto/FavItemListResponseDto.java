package cucumbermarket.cucumbermarketspring.domain.favourite.dto;

import cucumbermarket.cucumbermarketspring.domain.favourite.FavouriteItem;
import lombok.Getter;

@Getter
public class FavItemListResponseDto {
    private Long favid;
    private Long itemid;
    private String title ;
    private String city;
    private String street;
    private Long count;

    public FavItemListResponseDto(FavouriteItem entity, Long count){
        this.favid = entity.getId();
        this.itemid = entity.getItem().getId();
        this.title = entity.getItem().getTitle();
        this.city = entity.getItem().getAddress().getCity();
        this.street = entity.getItem().getAddress().getStreet1();
        this.count = count;
    }
}
