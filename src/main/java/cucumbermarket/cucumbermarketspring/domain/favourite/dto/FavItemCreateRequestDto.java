package cucumbermarket.cucumbermarketspring.domain.favourite.dto;

import cucumbermarket.cucumbermarketspring.domain.favourite.domain.favouriteitem.FavouriteItem;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Categories;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.upload.domain.photo.Photo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavItemCreateRequestDto {
    private Member member;
    private String title;
    private Categories categories;
    private int price;
    private String spec;
    private Photo photo;

    @Builder
    public FavItemCreateRequestDto(Member member, String title, Categories categories, int price, String spec, Photo photo){
        this.member = member;
        this.title = title;
        this.categories = categories;
        this.price = price;
        this.spec = spec;
        this.photo = photo;
    }

    public FavouriteItem toEntity(){
        return FavouriteItem.builder()
                .member(member)
                .title(title)
                .categories(categories)
                .price(price)
                .spec(spec)
                .build();
    }
}
