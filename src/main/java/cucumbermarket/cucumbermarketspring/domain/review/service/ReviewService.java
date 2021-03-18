package cucumbermarket.cucumbermarketspring.domain.review.service;

import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemResponseDto;
import cucumbermarket.cucumbermarketspring.domain.review.domain.Review;
import cucumbermarket.cucumbermarketspring.domain.review.domain.ReviewRepository;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewResponseDto;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional
    public Long save(ReviewCreateRequestDto requestDto){
        return ReviewRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, ReviewUpdateRequestDto requestDto){
        Review review = ReviewRepository.findByName(id).orElseThrow(() -> new IllegalArgumentException("문구 채우기"));

        review.update(requestDto.getTitle(), requestDto.getCategories(), requestDto.getPrice(), requestDto.getSold());

        return id;
    }

    @Transactional
    public Long delete(Long id){
        reviewRepository.deleteById(id);
    }

    public ItemResponseDto findByName(Long id){
        Review entity = reviewRepository.findByName(id).orElseThrow(() -> new IllegalArgumentException("문구 채우기"));

        return new ReviewResponseDto(entity);
    }
}
