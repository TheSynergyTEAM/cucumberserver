package cucumbermarket.cucumbermarketspring.service;

import cucumbermarket.cucumbermarketspring.domain.file.service.FileService;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Categories;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import cucumbermarket.cucumbermarketspring.domain.item.domain.ItemRepository;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemResponseDto;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemUpdateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.item.service.ItemService;
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
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private FileService fileService;

    @Autowired
    EntityManager entityManager;

   // @After
   // public void tearDown() throws Exception{
   //     itemRepository.deleteAll();
   // }

 //  @BeforeAll
 //   public void createMember() throws Exception{
  //     Address address = new Address();
  //     member = new Member("홍길동", "sj1234", address, LocalDate.now(), "hgd@gmail.com", "010-1234-5678", 0, "USER");
  //  }

    @Test
    public void 상품등록() throws Exception{
        //given
        /*ItemCreateRequestDto requestDto = ItemCreateRequestDto.builder()
                .member(member)
                .title(title)
                .categories(categories)
                .price(price)
                .spec(spec)
                .sold(false)
                .build();*/
        Member member = new Member("홍길동", "sj1234", new Address(), LocalDate.now(),
                "hgd@gmail.com", "010-1234-5678", 0, "USER");
        Item item = new Item(
                member, "싸게 팔아요", Categories.KID,
                10000, "잔기스 있어요", null, false);

        //when
        Long itemId = itemService.save(item);
        entityManager.persist(item);
        //entityManager.flush();

        //then
       // List<Item> itemList = itemRepository.findAll();
        assertEquals(item, itemRepository.getOne(itemId));
    }

    @Test
    public void 상품수정() throws Exception{
        //given
        Member member = new Member("홍길동", "sj1234", new Address(), LocalDate.now(),
                "hgd@gmail.com", "010-1234-5678", 0, "USER");
        Item item = new Item(
                member, "싸게 팔아요", Categories.KID,
                10000, "잔기스 있어요", null, false);
        Long itemId = itemService.save(item);
        entityManager.persist(item);

        System.out.println(item.getTitle());
        System.out.println(item.getCategories());
        System.out.println(item.getPrice());
        System.out.println(item.getSpec());

        String fixTitle = "싸게 팔아요(가격제시)";
        Categories fixCategory = Categories.DIGIT;
        int fixPrice = 5000;
        String fixSpec = "잔기스 있지만 괜찮아요";

        ItemUpdateRequestDto requestDto = ItemUpdateRequestDto.builder()
                .title(fixTitle)
                .categories(fixCategory)
                .price(fixPrice)
                .spec(fixSpec)
                .build();

        //when
        Long updateId = itemService.update(itemId, requestDto);
        entityManager.flush();

        //then
        assertThat(item.getId()).isEqualTo(updateId);

        System.out.println(item.getTitle());
        System.out.println(item.getCategories());
        System.out.println(item.getPrice());
        System.out.println(item.getSpec());
    }

    @Test
    public void 상품삭제() throws Exception{
        //given
        Member member = new Member("홍길동", "sj1234", new Address(), LocalDate.now(),
                "hgd@gmail.com", "010-1234-5678", 0, "USER");
        Item item = new Item(
                member, "싸게 팔아요", Categories.KID,
                10000, "잔기스 있어요", null, false);
   //     Item item = Item.builder()
   //             .member(member)
   //             .categories(Categories.KID)
   //             .price(10000)
   //             .spec("잔기스 있어용")
   //             .photo(null)
   //             .sold(false)
   //             .build();

        Long itemId = itemService.save(item);

        //when
        itemService.delete(itemId);

        //then
        assertThat(itemRepository.count()).isEqualTo(0);
        //assertThat(bookRepository.findAll(), IsEmptyCollection.empty());
    }

    @Test
    public void 상품_하나_조회() throws Exception{
        //given
        Member member = new Member("홍길동", "sj1234", new Address(), LocalDate.now(),
                "hgd@gmail.com", "010-1234-5678", 0, "USER");
        Item item1 = new Item(
                member, "싸게 팔아요", Categories.KID,
                10000, "잔기스 있어요", null, false);
        Item item2 = new Item(
                member, "급처분해요", Categories.WOMAN,
                20000, "A급 상품", null, false);

        Long itemId1 = itemService.save(item1);
        Long itemId2 = itemService.save(item2);
        entityManager.persist(item1);
        entityManager.persist(item2);

        //when
        ItemResponseDto itemResponseDto1 = itemService.findOne(itemId1);
        ItemResponseDto itemResponseDto2 = itemService.findOne(itemId2);

        //then
        assertThat(itemResponseDto1.getId()).isEqualTo(itemId1);
        assertThat(itemResponseDto2.getId()).isEqualTo(itemId2);
    }

    @Test
    public void 상품_모두_조회(){
        //given
        Member member = new Member("홍길동","sj1234", new Address(), LocalDate.now(),
                "hgd@gmail.com", "010-1234-5678", 0, "USER");
        Item item1 = new Item(
                member, "싸게 팔아요", Categories.KID,
                10000, "잔기스 있어요", null, false);
        Item item2 = new Item(
                member, "급처분해요", Categories.WOMAN,
                20000, "A급 상품", null, false);

        Long itemId1 = itemService.save(item1);
        Long itemId2 = itemService.save(item2);
        entityManager.persist(item1);
        entityManager.persist(item2);

        //when
        List<Item> itemList = itemService.findAll();

        //then
        assertThat(itemList.get(0).getId()).isEqualTo(itemId1);
        assertThat(itemList.get(1).getId()).isEqualTo(itemId2);
    }
}
