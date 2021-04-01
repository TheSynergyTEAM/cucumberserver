package cucumbermarket.cucumbermarketspring.service;

import cucumbermarket.cucumbermarketspring.domain.favourite.domain.FavouriteItem;
import cucumbermarket.cucumbermarketspring.domain.favourite.domain.FavouriteItemRepository;
import cucumbermarket.cucumbermarketspring.domain.favourite.dto.FavItemSearchDto;
import cucumbermarket.cucumbermarketspring.domain.favourite.service.FavouriteService;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Categories;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
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
public class FavouriteServiceTest {
    @Autowired
    private FavouriteItemRepository favItemRepository;

    @Autowired
    private FavouriteService favService;

    @Autowired
    EntityManager entityManager;

    @Test
    public void 찜하기_생성() throws Exception{
        //given
        Member member = new Member("홍길동", "sj1234", new Address(), LocalDate.now(),
                "hgd@gmail.com", "010-1234-5678", 0, "USER");
        Item item = new Item(
                member, "싸게 팔아요", Categories.KID,
                10000, "잔기스 있어요", null, false);

        FavouriteItem favItem = new FavouriteItem(item, member);

        //when
        Long favId = favService.createFavouriteItem(favItem);
        entityManager.persist(favItem);

        //then
        assertThat(favItem.getId()).isEqualTo(favId);
    }

    @Test
    public void 찜하기_삭제() throws Exception{
        //given
        Member member = new Member("홍길동", "sj1234", new Address(), LocalDate.now(),
                "hgd@gmail.com", "010-1234-5678", 0, "USER");
        Item item = new Item(
                member, "싸게 팔아요", Categories.KID,
                10000, "잔기스 있어요", null, false);
        FavouriteItem favItem = new FavouriteItem(item, member);

        Long favItemId = favService.createFavouriteItem(favItem);

        //when
        favService.removeFavouriteItem(favItemId);

        //then
        assertThat(favItemRepository.count()).isEqualTo(0);
    }

    @Test
    public void 찜하기_하나_조회() throws Exception{
        //given
        Member member = new Member("홍길동", "sj1234", new Address(), LocalDate.now(),
                "hgd@gmail.com", "010-1234-5678", 0, "USER");
        Item item1 = new Item(
                member, "싸게 팔아요", Categories.KID,
                10000, "잔기스 있어요", null, false);
        Item item2 = new Item(
                member, "급처분해요", Categories.WOMAN,
                20000, "A급 상품", null, false);

        FavouriteItem favItem1 = new FavouriteItem(item1, member);
        FavouriteItem favItem2 = new FavouriteItem(item2, member);

        Long favId1 = favService.createFavouriteItem(favItem1);
        Long favId2 = favService.createFavouriteItem(favItem2);
        entityManager.persist(favItem1);
        entityManager.persist(favItem2);

        //when
        FavItemSearchDto favSearchDto1 = favService.findOne(favId1);
        FavItemSearchDto favSearchDto2 = favService.findOne(favId2);

        //then
        assertThat(favSearchDto1.getId()).isEqualTo(favId1);
        assertThat(favSearchDto2.getId()).isEqualTo(favId2);
    }

    @Test
    public void 찜하기_모두_조회() throws Exception{
        //given
        Member member = new Member("홍길동","sj1234", new Address(), LocalDate.now(),
                "hgd@gmail.com", "010-1234-5678", 0, "USER");
        Item item1 = new Item(
                member, "싸게 팔아요", Categories.KID,
                10000, "잔기스 있어요", null, false);
        Item item2 = new Item(
                member, "급처분해요", Categories.WOMAN,
                20000, "A급 상품", null, false);

        FavouriteItem favItem1 = new FavouriteItem(item1, member);
        FavouriteItem favItem2 = new FavouriteItem(item2, member);

        Long favId1 = favService.createFavouriteItem(favItem1);
        Long favId2 = favService.createFavouriteItem(favItem2);
        entityManager.persist(favItem1);
        entityManager.persist(favItem2);

        //when
        List<FavouriteItem> favItemList = favService.findAll();

        //then
        assertThat(favItemList.get(0).getId()).isEqualTo(favId1);
        assertThat(favItemList.get(1).getId()).isEqualTo(favId2);
    }
}
