package cucumbermarket.cucumbermarketspring.domain.item.dto;

import cucumbermarket.cucumbermarketspring.domain.item.domain.Categories;
import cucumbermarket.cucumbermarketspring.domain.upload.domain.photo.Photo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ItemUpdateRequestDto {
    private String title;
    private Categories categories;
    private int price;
    private String spec;
 //   private List<Photo> photo;
    private Boolean sold;

    @Builder
    public ItemUpdateRequestDto(String title, Categories categories, int price, String spec, Boolean sold){
        this.title = title;
        this.categories = categories;
        this.price = price;
  //      this.photo = photo;
        this.sold = sold;
    }
}
