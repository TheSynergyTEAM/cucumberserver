package cucumbermarket.cucumbermarketspring.domain.review.dto;

import cucumbermarket.cucumbermarketspring.domain.review.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto {
    private Long id;
    private String item;
    private String seller;
    private String buyer;
    private int ratingScore;
    private String content;
    private String city;
    private String street;
    private LocalDateTime created;
    private LocalDateTime updated;

    public ReviewResponseDto(Review entity){
        this.id = entity.getId();
        this.item = entity.getItem().getTitle();
        this.seller = entity.getItem().getMember().getName();
        this.buyer = entity.getMember().getName();
        this.ratingScore = entity.getRatingScore();
        this.content = entity.getContent();
        this.city = entity.getItem().getAddress().getCity();
        this.street = entity.getItem().getAddress().getStreet1();
        this.created = entity.getCreated();
        this.updated = entity.getModified();
    }
}
