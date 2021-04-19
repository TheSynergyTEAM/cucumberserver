package cucumbermarket.cucumbermarketspring.domain.favourite.dto;

import cucumbermarket.cucumbermarketspring.domain.favourite.FavouriteItem;
import lombok.Getter;

@Getter
public class FavItemResponseDto {
    private Long id;
    private Long itemId;
    private String item ;
    private String city;
    private String street;

    public FavItemResponseDto(FavouriteItem entity){
        this.id = entity.getId();
        this.itemId = entity.getItem().getId();
        this.item = entity.getItem().getTitle();
        this.city = entity.getItem().getAddress().getCity();
        this.street = entity.getItem().getAddress().getStreet1();
    }
}
