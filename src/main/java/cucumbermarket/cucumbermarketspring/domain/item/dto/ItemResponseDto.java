package cucumbermarket.cucumbermarketspring.domain.item.dto;

import cucumbermarket.cucumbermarketspring.domain.file.domain.File;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Categories;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import lombok.Getter;

import java.util.List;

@Getter
public class ItemResponseDto {
    private Long id;
    private Member member;
    private String title;
    private Categories categories;
    private int price;
    private String spec;
    private List<File> photo;
    private Boolean sold;

    public ItemResponseDto(Item entity){
        this.id = entity.getId();
        this.member = entity.getMember();
        this.title = entity.getTitle();
        this.categories = entity.getCategories();
        this.price = entity.getPrice();
        this.spec = entity.getSpec();
        this.photo = entity.getPhoto();
        this.sold = entity.getSold();
    }
}
