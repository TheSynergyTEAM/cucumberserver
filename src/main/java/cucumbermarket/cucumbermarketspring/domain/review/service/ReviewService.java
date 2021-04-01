package cucumbermarket.cucumbermarketspring.domain.review.service;

import cucumbermarket.cucumbermarketspring.domain.review.domain.Review;
import cucumbermarket.cucumbermarketspring.domain.review.domain.ReviewRepository;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewResponseDto;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional
    public Long createReview(Review review){
        return reviewRepository.save(review).getId();
    }
  //  public Long createReview(ReviewCreateRequestDto requestDto){
  //      return reviewRepository.save(requestDto.toEntity()).getId();
  //  }

    @Transactional
    public Long updateReview(Long id, ReviewUpdateRequestDto requestDto){
        Review review = reviewRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다. id = " + id));

        review.update(requestDto.getContent(), requestDto.getRatingScore());

        return id;
    }

    @Transactional
    public void delete(Long id){
        Review review = reviewRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));

        reviewRepository.delete(review);
    }

    public ReviewResponseDto findOne(Long id){
        Review entity = reviewRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다. id = " + id));

        return new ReviewResponseDto(entity);
    }

    public List<Review> findAll(){
        return reviewRepository.findAll();
    }

}
