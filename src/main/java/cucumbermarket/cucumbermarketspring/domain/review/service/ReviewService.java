package cucumbermarket.cucumbermarketspring.domain.review.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import cucumbermarket.cucumbermarketspring.domain.review.QReview;
import cucumbermarket.cucumbermarketspring.domain.review.Review;
import cucumbermarket.cucumbermarketspring.domain.review.ReviewRepository;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewListResponseDto;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewResponseDto;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @PersistenceContext
    private EntityManager em;

    /**
     * 리뷰생성
     * */
    @Transactional
    public Long createReview(ReviewCreateRequestDto requestDto){
        Long id = reviewRepository.save(requestDto.toEntity()).getId();
        return id;
    }

    /**
     * 리뷰수정
     * */
    @Transactional
    public ReviewResponseDto updateReview(Long id, ReviewUpdateRequestDto requestDto){
        Review review = reviewRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));

        review.update(requestDto.getContent(), requestDto.getRatingScore());

        ReviewResponseDto reviewResponseDto = this.findOne(id);
        return reviewResponseDto;
    }

    /**
     * 리뷰삭제
     * */
    @Transactional
    public void delete(Long id){
        Review review = reviewRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));

        reviewRepository.delete(review);
    }

    /**
     * 리뷰 개별 조회
     * */
    @Transactional(readOnly = true)
    public ReviewResponseDto findOne(Long id){
        Review entity = reviewRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));

        return new ReviewResponseDto(entity);
    }

    /**
     * 리뷰 전체 조회(구매+판매)
     * */
    @Transactional(readOnly = true)
    public List<ReviewListResponseDto> findAll(Long id){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QReview review = QReview.review;

        List<Review> reviewList = queryFactory
                .selectFrom(review)
                .where(review.item.member.id.eq(id).or(review.member.id.eq(id)))
                .fetch();

        return reviewList.stream()
                .map(ReviewListResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 구매한 상품 관련 리뷰 전체 조회
     * */
    @Transactional(readOnly = true)
    public List<ReviewListResponseDto> findAllByBuyer(Long id){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QReview review = QReview.review;

        List<Review> reviewList = queryFactory
                .selectFrom(review)
                .innerJoin(review.member)
                .where(review.member.id.eq(id))
                .fetch();

        return reviewList.stream()
                .map(ReviewListResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 판매한 상품 관련 리뷰 전체 조회
     * */
    @Transactional(readOnly = true)
    public List<ReviewListResponseDto> findAllBySeller(Long id) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QReview review = QReview.review;

        List<Review> soldReviewList = queryFactory
                .selectFrom(review)
                .innerJoin(review.item.member)
                .where(review.item.member.id.eq(id))
                .fetch();

        return soldReviewList.stream()
                .map(ReviewListResponseDto::new)
                .collect(Collectors.toList());
    }
}
