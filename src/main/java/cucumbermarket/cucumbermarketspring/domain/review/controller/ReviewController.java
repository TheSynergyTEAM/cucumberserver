package cucumbermarket.cucumbermarketspring.domain.review.controller;

import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewListResponseDto;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewResponseDto;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewUpdateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.review.service.ReviewService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    /**
     * 리뷰 생성
     * */
    @PostMapping("/review")
    public CreateReviewResponse save(@RequestBody ReviewCreateRequestDto requestDto){
        Long id = reviewService.createReview(requestDto);
        return new CreateReviewResponse(id);
    }

    /**
     * 리뷰 수정
     * */
    @PutMapping("/review/{id}")
    public ReviewResponseDto update(@PathVariable Long id, @RequestBody ReviewUpdateRequestDto requestDto){
        return reviewService.updateReview(id, requestDto);
    }

    /**
     * 리뷰 삭제
     * */
    @DeleteMapping("/review/{id}")
    public void delete (@PathVariable Long id){
        reviewService.delete(id);
    }

    /**
     * 리뷰 개별 조회
     * */
    @GetMapping("/review/{id}")
    public ReviewResponseDto searchOne(@PathVariable Long id){
        return reviewService.findOne(id);
    }

    /**
     * 리뷰 전체 조회
     * */
    @GetMapping("/review")
    public List<ReviewListResponseDto> searchAll(@RequestParam("user") Long id){
        return reviewService.findAll(id);
    }

    /**
     * 판매 리뷰 전체 조회
     * */
    @GetMapping("/review/sell")
    public List<ReviewListResponseDto> searchAllSold(@RequestParam("user") Long id){
        return reviewService.findAllBySeller(id);
    }

    /**
     * 구매 리뷰 전체 조회
     * */
    @GetMapping("/review/buy")
    public List<ReviewListResponseDto> searchAllBought(@RequestParam("user") Long id){
        return reviewService.findAllByBuyer(id);
    }


    @Data
    static class CreateReviewResponse {
        private Long id;

        public CreateReviewResponse(Long id) {
            this.id = id;
        }
    }
}
