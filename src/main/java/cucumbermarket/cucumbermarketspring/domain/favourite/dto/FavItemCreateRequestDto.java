package cucumbermarket.cucumbermarketspring.domain.favourite.dto;

import cucumbermarket.cucumbermarketspring.domain.favourite.FavouriteItem;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavItemCreateRequestDto {
    private Item item;
    private Member member;

    @Builder
    public FavItemCreateRequestDto(Item item, Member member){
        this.item = item;
        this.member = member;
    }

    public FavouriteItem toEntity(){
        return FavouriteItem.builder()
                .item(item)
                .member(member)
                .build();
    }
}
