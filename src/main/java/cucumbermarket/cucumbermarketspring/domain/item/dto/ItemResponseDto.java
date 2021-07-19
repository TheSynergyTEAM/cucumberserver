package cucumbermarket.cucumbermarketspring.domain.item.dto;

import cucumbermarket.cucumbermarketspring.domain.item.Item;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ItemResponseDto {
    private Long id;
    private String member;
    private String title;
    private String categories;
    private int price;
    private String spec;
    private String city;
    private String street;
    private Boolean sold;
    private LocalDateTime created;
    private int views;
    private Long favCnt;
    private Boolean like;
    private List<Long> fileid;
    private Long buyerId;

    public ItemResponseDto(Item entity, List<Long> fileId, Long favCnt, Boolean like){
        this.id = entity.getId();
        this.member = entity.getMember().getName();
        this.title = entity.getTitle();
        this.categories = entity.getCategories().getValue();
        this.price = entity.getPrice();
        this.spec = entity.getSpec();
        this.city = entity.getAddress().getCity();
        this.street = entity.getAddress().getStreet1();
        this.sold = entity.getSold();
        this.created = entity.getCreated();
        this.views = entity.getViews();
        this.buyerId = entity.getBuyerId();
        this.favCnt = favCnt;
        this.like = like;
        this.fileid = fileId;
    }
}
