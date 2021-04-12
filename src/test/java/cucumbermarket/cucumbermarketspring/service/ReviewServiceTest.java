package cucumbermarket.cucumbermarketspring.service;

import cucumbermarket.cucumbermarketspring.domain.item.Categories;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
import cucumbermarket.cucumbermarketspring.domain.review.Review;
import cucumbermarket.cucumbermarketspring.domain.review.ReviewRepository;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewResponseDto;
import cucumbermarket.cucumbermarketspring.domain.review.dto.ReviewUpdateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.review.service.ReviewService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReviewServiceTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    EntityManager entityManager;

    @Test
    public void 리뷰생성() throws Exception{
        Member member = new Member("홍길동", "sj1234", new Address(), LocalDate.now(),
                "hgd@gmail.com", "010-1234-5678", 0, "USER");
        Item item = new Item(
                member, "싸게 팔아요", Categories.KID,
                10000, "잔기스 있어요", null, false);

        Review review = new Review(item, member, 5, "깔끔했어요.");
        //when
        Long reviewId = reviewService.createReview(review);
        entityManager.persist(review);

        //then
        assertThat(review.getId()).isEqualTo(reviewId);
    }

    @Test
    public void 리뷰수정() throws Exception{
        //given
        Member member = new Member("홍길동", "sj1234", new Address(), LocalDate.now(),
                "hgd@gmail.com", "010-1234-5678", 0, "USER");
        Item item = new Item(
                member, "싸게 팔아요", Categories.KID,
                10000, "잔기스 있어요", null, false);

        Review review = new Review(item, member, 5, "깔끔했어요.");

        Long reviewId = reviewService.createReview(review);
        entityManager.persist(review);

        System.out.println(review.getContent());
        System.out.println(review.getRatingScore());

        String content = "다시보니 잔기스가 ㅡㅡ";
        int ratingScore = 3;

        ReviewUpdateRequestDto requestDto = ReviewUpdateRequestDto.builder()
                .ratingScore(ratingScore)
                .content(content)
                .build();

        //when
        Long updateId = reviewService.updateReview(reviewId, requestDto);
        entityManager.flush();

        //then
        assertThat(review.getId()).isEqualTo(updateId);

        System.out.println(review.getContent());
        System.out.println(review.getRatingScore());
    }

    @Test
    public void 리뷰삭제() throws Exception{
        //given
        Member member = new Member("홍길동", "sj1234", new Address(), LocalDate.now(),
                "hgd@gmail.com", "010-1234-5678", 0, "USER");
        Item item = new Item(
                member, "싸게 팔아요", Categories.KID,
                10000, "잔기스 있어요", null, false);
        Review review = new Review(item, member, 5, "깔끔했어요.");

        Long reviewId = reviewService.createReview(review);

        //when
        reviewService.delete(reviewId);

        //then
        assertThat(reviewRepository.count()).isEqualTo(0);
    }

    @Test
    public void 리뷰_하나_조회() throws Exception{
        //given
        Member member = new Member("홍길동", "sj1234", new Address(), LocalDate.now(),
                "hgd@gmail.com", "010-1234-5678", 0, "USER");
        Item item1 = new Item(
                member, "싸게 팔아요", Categories.KID,
                10000, "잔기스 있어요", null, false);
        Item item2 = new Item(
                member, "급처분해요", Categories.WOMAN,
                20000, "A급 상품", null, false);

        Review review1 = new Review(item1, member, 5, "깔끔했어요.");
        Review review2 = new Review(item2, member, 5, "만족스러웠어요.");

        Long reviewId1 = reviewService.createReview(review1);
        Long reviewId2 = reviewService.createReview(review2);
        entityManager.persist(review1);
        entityManager.persist(review2);

        //when
        ReviewResponseDto reviewResponseDto1 = reviewService.findOne(reviewId1);
        ReviewResponseDto reviewResponseDto2 = reviewService.findOne(reviewId2);

        //then
        assertThat(reviewResponseDto1.getId()).isEqualTo(reviewId1);
        assertThat(reviewResponseDto2.getId()).isEqualTo(reviewId2);
    }

    @Test
    public void 리뷰_전부_조회() throws Exception{
       //given
        Member member = new Member("홍길동","sj1234", new Address(), LocalDate.now(),
                "hgd@gmail.com", "010-1234-5678", 0, "USER");
        Item item1 = new Item(
                member, "싸게 팔아요", Categories.KID,
                10000, "잔기스 있어요", null, false);
        Item item2 = new Item(
                member, "급처분해요", Categories.WOMAN,
                20000, "A급 상품", null, false);

        Review review1 = new Review(item1, member, 5, "깔끔했어요.");
        Review review2 = new Review(item2, member, 5, "만족스러웠어요.");

        Long reviewId1 = reviewService.createReview(review1);
        Long reviewId2 = reviewService.createReview(review2);
        entityManager.persist(review1);
        entityManager.persist(review2);

        //when
        List<Review> reviewList = reviewService.findAll();

        //then
        assertThat(reviewList.get(0).getId()).isEqualTo(reviewId1);
        assertThat(reviewList.get(1).getId()).isEqualTo(reviewId2);
    }
}
