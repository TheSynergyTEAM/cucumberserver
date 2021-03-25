package cucumbermarket.cucumbermarketspring.domain.review.dto;

import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.review.domain.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewCreateRequestDto {
    private Item item;
    private Member member;
    private int ratingScore;
    private String content;

    @Builder
    public ReviewCreateRequestDto(Item item, Member member, int ratingScore, String content){
        this.item = item;
        this.member = member;
        this.ratingScore = ratingScore;
        this.content = content;
    }

    public Review toEntity(){
        return Review.builder()
                .item(item)
                .member(member)
                .ratingScore(ratingScore)
                .content(content)
                .build();
    }
}
