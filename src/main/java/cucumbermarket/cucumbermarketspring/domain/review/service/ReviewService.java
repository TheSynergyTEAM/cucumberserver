package cucumbermarket.cucumbermarketspring.domain.review.service;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import cucumbermarket.cucumbermarketspring.domain.item.QItem;
import cucumbermarket.cucumbermarketspring.domain.member.QMember;
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
    public Long updateReview(Long id, ReviewUpdateRequestDto requestDto){
        Review review = reviewRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다. id = " + id));

        review.update(requestDto.getContent(), requestDto.getRatingScore());

        return id;
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
     * 리뷰 하나 조회
     * */
    @Transactional(readOnly = true)
    public ReviewResponseDto findOne(Long id){
        Review entity = reviewRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다. id = " + id));

        return new ReviewResponseDto(entity);
    }

    /**
     * 리뷰 전체 조회
     * */
    @Transactional(readOnly = true)
    public List<ReviewListResponseDto> findAll(){
        return reviewRepository.findAll().stream()
                .map(ReviewListResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 구매 리뷰 전체 조회
     * */
    @Transactional(readOnly = true)
    public List<ReviewListResponseDto> findAllByBuyer(String name){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QReview review = QReview.review;
        QMember seller = QItem.item.member;
       // QMember seller = QReview.review.item.member;
       // QMember buyer = QReview.review.member;

        List<Review> reviewList = queryFactory.selectFrom(review)
                .where(review.item.member.name.eq(name), review.member.name.ne(name))
                .fetch();
       /* Predicate predicate = seller.name.equalsIgnoreCase(name)
                .and(buyer.name.eq(name));
        Iterator<Review> it = reviewRepository.findAll(predicate).iterator();
        List<Review> reviewList = Lists.newArrayList(it);*/
        return reviewList.stream()
                .map(ReviewListResponseDto::new)
                .collect(Collectors.toList());
        /*    return reviewRepository.findByBuyer(name).stream()
                .map(ReviewListResponseDto::new)
                .collect(Collectors.toList());*/
    }

    /**
     * 판매 리뷰 전체 조회
     * */
    @Transactional(readOnly = true)
    public List<ReviewListResponseDto> findAllBySeller(String name){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QReview review = QReview.review;
      //  QMember seller = QReview.review.item.member;
      //  QMember buyer = QReview.review.member;

        JPAQuery<Review> query = queryFactory.selectFrom(review);

     //   if(item.member)

        /*Predicate predicate = buyer.name.equalsIgnoreCase(name)
                .and(seller.name.eq(name));
        Iterator<Review> it = reviewRepository.findAll(predicate).iterator();
        List<Review> reviewList = Lists.newArrayList(it);*/
        /*return reviewRepository.findBySeller(name).stream()
                .map(ReviewListResponseDto::new)
                .collect(Collectors.toList());*/
        List<Review> reviewList = queryFactory.selectFrom(review)
                .where(review.item.member.name.eq(name), review.member.name.ne(name))
                .fetch();

        return reviewList.stream()
                .map(ReviewListResponseDto::new)
                .collect(Collectors.toList());
    }
}
