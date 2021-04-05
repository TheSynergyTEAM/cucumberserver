package cucumbermarket.cucumbermarketspring.domain.item.dto;

import cucumbermarket.cucumbermarketspring.domain.item.domain.Categories;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemUpdateRequestDto {
    private String title;
    private Categories categories;
    private int price;
    private String spec;
    private Boolean sold;

    @Builder
    public ItemUpdateRequestDto(String title, Categories categories, int price, String spec, Boolean sold){
        this.title = title;
        this.categories = categories;
        this.price = price;
        this.spec = spec;
        this.sold = sold;
    }
}
