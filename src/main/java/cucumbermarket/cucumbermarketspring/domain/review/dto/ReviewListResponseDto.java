package cucumbermarket.cucumbermarketspring.domain.review.dto;

import cucumbermarket.cucumbermarketspring.domain.review.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewListResponseDto {
    private Long id;
    private String itemtitle;
    private String seller;
    private String buyer;
    private int ratingscore;
    private String content;
    private String city;
    private String street;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Long thumbnailid;

    public ReviewListResponseDto(Review entity){
        this.id = entity.getId();
        this.itemtitle = entity.getItem().getTitle();
        this.seller = entity.getItem().getMember().getName();
        this.buyer = entity.getMember().getName();
        this.ratingscore = entity.getRatingScore();
        this.content = entity.getContent();
        this.city = entity.getItem().getAddress().getCity();
        this.street = entity.getItem().getAddress().getStreet1();
        this.created = entity.getCreated();
        this.updated = entity.getModified();

        if(!entity.getPhoto().isEmpty())
            this.thumbnailid = entity.getPhoto().get(0).getId();
    }
}
