package cucumbermarket.cucumbermarketspring.domain.item.dto;

import cucumbermarket.cucumbermarketspring.domain.item.domain.Categories;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ItemListResponseDto {
    private Long id;
    private Member member;
    private String title;
    private Categories categories;
    private int price;
    private String spec;
    private Boolean sold;
    private LocalDateTime created;

    public ItemListResponseDto(Item entity){
        this.id = entity.getId();
        this.member = entity.getMember();
        this.title = entity.getTitle();
        this.categories = entity.getCategories();
        this.price = entity.getPrice();
        this.spec = entity.getSpec();
        this.sold = entity.getSold();
        this.created = entity.getCreated();
    }
}
