package cucumbermarket.cucumbermarketspring.domain.item.dto;

import cucumbermarket.cucumbermarketspring.domain.item.Item;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ItemListResponseDto {
    private Long id;
    private String member;
    private String city;
    private String street;
    private String title;
    private String categories;
    private int price;
    private String spec;
    private Boolean sold;
    private LocalDateTime created;

    public ItemListResponseDto(Item entity){
        this.id = entity.getId();
        this.member = entity.getMember().getUsername();
        this.city = entity.getAddress().getCity();
        this.street = entity.getAddress().getStreet1();
        this.title = entity.getTitle();
        this.categories = entity.getCategories().getValue();
        this.price = entity.getPrice();
        this.spec = entity.getSpec();
        this.sold = entity.getSold();
        this.created = entity.getCreated();
    }
}
