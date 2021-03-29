package cucumbermarket.cucumbermarketspring.domain.review.controller;

import cucumbermarket.cucumbermarketspring.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReviewApiController {
    private final ReviewService reviewService;

    /*@PostMapping("/review")
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
    }*/
}
