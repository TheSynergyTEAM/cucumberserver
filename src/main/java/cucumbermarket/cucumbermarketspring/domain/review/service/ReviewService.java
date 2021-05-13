package cucumbermarket.cucumbermarketspring.domain.review.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import cucumbermarket.cucumbermarketspring.domain.file.Photo;
import cucumbermarket.cucumbermarketspring.domain.file.PhotoRepository;
import cucumbermarket.cucumbermarketspring.domain.file.util.FileHandler;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PhotoRepository photoRepository;
    private final FileHandler fileHandler;

    @PersistenceContext
    private EntityManager em;

    /**
     * 리뷰생성
     * */
    @Transactional
    public Long createReview(ReviewCreateRequestDto requestDto, List<MultipartFile> files) throws Exception{

        Review review = new Review(
                requestDto.getItem(),
                requestDto.getMember(),
                requestDto.getRatingScore(),
                requestDto.getContent()
        );

        List<Photo> photoList = fileHandler.parseFileInfo(review, files);

        if(!CollectionUtils.isEmpty(photoList)){
            for(Photo photo : photoList)
                review.addPhoto(photoRepository.save(photo));
        }

        return reviewRepository.save(review).getId();
    }

    /**
     * 리뷰수정
     * */
    @Transactional
    public void updateReview(Long id, ReviewUpdateRequestDto requestDto, List<MultipartFile> files) throws Exception {
        Review review = reviewRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));

        List<Photo> photoList = fileHandler.parseFileInfo(review, files);

        if(!photoList.isEmpty()){
            for(Photo photo : photoList) {
                photoRepository.save(photo);
            }
        }

        review.update(requestDto.getContent(), requestDto.getRatingScore());
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
    public ReviewResponseDto findOne(Long id, List<Long> fileId){
        Review entity = reviewRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));

        return new ReviewResponseDto(entity, fileId);
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
