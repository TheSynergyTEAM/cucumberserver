package cucumbermarket.cucumbermarketspring.domain.review.dto;

import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.review.domain.Review;
import lombok.Getter;

@Getter
public class ReviewResponseDto {
    private Long id;
    private Item item;
    private Member member;
    private int ratingScore;
    private String content;

    public ReviewResponseDto(Review entity){
        this.id = entity.getId();
        this.item = entity.getItem();
        this.member = entity.getMember();
        this.ratingScore = entity.getRatingScore();
        this.content = entity.getContent();
    }
}
