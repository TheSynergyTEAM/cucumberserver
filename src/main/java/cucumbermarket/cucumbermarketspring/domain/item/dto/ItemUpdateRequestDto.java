package cucumbermarket.cucumbermarketspring.domain.item.dto;

import cucumbermarket.cucumbermarketspring.domain.file.domain.File;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Categories;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ItemUpdateRequestDto {
    private String title;
    private Categories categories;
    private int price;
    private String spec;
    private List<File> photo;
    private Boolean sold;

    @Builder
    public ItemUpdateRequestDto(String title, Categories categories, int price, String spec, List<File> photo, Boolean sold){
        this.title = title;
        this.categories = categories;
        this.price = price;
        this.spec = spec;
        this.photo = photo;
        this.sold = sold;
    }
}
