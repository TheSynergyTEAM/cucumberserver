package cucumbermarket.cucumbermarketspring.domain.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateRequestDto {
    private int ratingScore;
    private String content;

    @Builder
    public ReviewUpdateRequestDto(int ratingScore, String content){
        this.ratingScore = ratingScore;
        this.content = content;
    }

}
