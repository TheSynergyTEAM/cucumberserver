package cucumbermarket.cucumbermarketspring.domain.favourite.dto;

import cucumbermarket.cucumbermarketspring.domain.favourite.domain.FavouriteItem;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import lombok.Getter;

@Getter
public class FavItemSearchDto {
    private Long id;
    private Item item;
    private Member member;

    public FavItemSearchDto(FavouriteItem entity){
        this.id = entity.getId();
        this.item = entity.getItem();
        this.member = entity.getMember();
    }
}
