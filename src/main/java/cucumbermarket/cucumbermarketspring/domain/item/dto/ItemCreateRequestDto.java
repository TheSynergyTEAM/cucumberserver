package cucumbermarket.cucumbermarketspring.domain.item.dto;

import cucumbermarket.cucumbermarketspring.domain.file.domain.File;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Categories;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ItemCreateRequestDto {
    private Member member;
    private String title;
    private Categories categories;
    private int price;
    private String spec;
    private List<File> photo;
    private Boolean sold;

    @Builder
    public ItemCreateRequestDto(Member member, String title, Categories categories, int price, String spec, List<File> photo, Boolean sold){
        this.member = member;
        this.title = title;
        this.categories = categories;
        this.price = price;
        this.spec = spec;
        this.photo = photo;
        this.sold = sold;
    }

    public Item toEntity(){
        return Item.builder()
                .member(member)
                .title(title)
                .categories(categories)
                .price(price)
                .spec(spec)
                .photo(photo)
                .sold(sold)
                .build();
    }
}
