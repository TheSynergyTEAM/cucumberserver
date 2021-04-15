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
    private LocalDateTime created;

    public ReviewResponseDto(Review entity){
        this.id = entity.getId();
        this.item = entity.getItem().getTitle();
        this.seller = entity.getItem().getMember().getName();
        this.buyer = entity.getMember().getName();
        this.ratingScore = entity.getRatingScore();
        this.content = entity.getContent();
        this.created = entity.getCreated();
    }
}
