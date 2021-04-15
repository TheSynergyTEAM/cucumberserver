package cucumbermarket.cucumbermarketspring.domain.item.dto;

import cucumbermarket.cucumbermarketspring.domain.item.Categories;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ItemResponseDto {
    private Long id;
    private String memberName;
    private String title;
    private Categories categories;
    private int price;
    private String spec;
    private String city;
    private String street;
    private Boolean sold;
    private LocalDateTime created;

    public ItemResponseDto(Item entity){
        this.id = entity.getId();
        this.memberName = entity.getMember().getName();
        this.title = entity.getTitle();
        this.categories = entity.getCategories();
        this.price = entity.getPrice();
        this.spec = entity.getSpec();
        this.city = entity.getAddress().getCity();
        this.street = entity.getAddress().getStreet1();
        this.sold = entity.getSold();
        this.created = entity.getCreated();
    }
}
