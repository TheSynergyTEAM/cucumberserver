package cucumbermarket.cucumbermarketspring.domain.review.controller;

import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewResponseDto;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewUpdateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    /**
     * 리뷰 생성
     * */
    @PostMapping("/review")
    public Long save(@RequestBody ReviewCreateRequestDto requestDto){
        return reviewService.createReview(requestDto);
    }

    /**
     * 리뷰 수정
     * */
    @PutMapping("/review/{id}")
    public Long update(@PathVariable Long id, @RequestBody ReviewUpdateRequestDto requestDto){
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
     * 리뷰 조회
     * */
    @GetMapping("/review/{id}")
    public ReviewResponseDto findOne(@PathVariable Long id){
        return reviewService.findOne(id);
    }

    /**
     * 리뷰 전체 조회
     * */
   /* @GetMapping("/review")
    public List<ReviewListResponseDto> findAll(){
        return reviewService.findAll();
    }*/

    /**
     * 판매 리뷰 전체 조회
     * */
 /*   @GetMapping("/review/sell")
    public List<ReviewListResponseDto> searchAllSold(@RequestParam("name") String name){
        return reviewService.findAllBySeller(name);
    }*/

    /**
     * 구매 리뷰 전체 조회
     * */
 /*   @GetMapping("/review/buy")
    public List<ReviewListResponseDto> searchAllBought(@RequestParam("name") String name){
        return reviewService.findAllByBuyer(name);
    }*/
}
