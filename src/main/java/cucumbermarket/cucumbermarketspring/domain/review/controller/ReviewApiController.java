package cucumbermarket.cucumbermarketspring.domain.review.controller;

import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemResponseDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemUpdateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewResponseDto;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewUpdateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ReviewApiController {
    private final ReviewService reviewService;

    @PostMapping("/review")
    public Long save(@RequestBody ReviewCreateRequestDto requestDto){
        return reviewService.save(requestDto);
    }

    @PutMapping("/review/{id}")
    public Long update(@PathVariable Long id, @RequestBody ReviewUpdateRequestDto requestDto){
        return reviewService.update(id, requestDto);
    }

    @GetMapping("/review/{id}")
    public ReviewResponseDto findByName(@PathVariable Long id){
        return reviewService.findByName();
    }
}
