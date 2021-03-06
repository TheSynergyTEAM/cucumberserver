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
    private String sold;
    private LocalDateTime created;
    private int views;
    private Long favCnt;
    private Boolean like;
    private Long thumbnailid;


    public ItemListResponseDto(Item entity, Long favCnt, Boolean like) {
        this.id = entity.getId();
        this.member = entity.getMember().getUsername();
        this.city = entity.getAddress().getCity();
        this.street = entity.getAddress().getStreet1();
        this.title = entity.getTitle();
        this.categories = entity.getCategories().getValue();
        this.price = entity.getPrice();
        this.sold = entity.getSold().getValue();
        this.created = entity.getCreated();
        this.views = entity.getViews();
        this.favCnt = favCnt;
        this.like = like;

        if(!entity.getPhoto().isEmpty())
            this.thumbnailid = entity.getPhoto().get(0).getId();
        else
            this.thumbnailid = 0L;
    }
}
