package cucumbermarket.cucumbermarketspring.service;

import cucumbermarket.cucumbermarketspring.domain.item.domain.Categories;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import cucumbermarket.cucumbermarketspring.domain.item.domain.ItemRepository;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.item.service.ItemService;
import cucumbermarket.cucumbermarketspring.domain.member.Address;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        ItemService.class,
        ItemRepository.class,
        MemberRepository.class
})
public class ItemServiceTest {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private MemberRepository memberRepository;

   // @After
   // public void tearDown() throws Exception{
   //     itemRepository.deleteAll();
   // }

   @BeforeAll
    public void createMember(){
        Address address = Address.builder()
                .state("충청북도")
                .city("청주시")
                .street1("흥덕구")
                .street2("수곡2동")
                .zipcode("19235")
                .build();

        LocalDate birthDate = LocalDate.of(1993,7,2);
        memberRepository.save(Member.builder()
                .name("김연아")
                .password("sj1234")
                .email("kya@gmail.com")
                .contact("010-1111-1111")
                .address(address)
                .birthdate(birthDate)
                .build());
    }

    @Test
    public void 상품등록(){
        //given
        List<Member> memberList = memberRepository.findAll();
        Member member = memberList.get(0);
        String title = "옷 팝니다";
        Categories categories = Categories.WOMAN;
        int price = 10000;
        String spec = "떨이 판매합니다. 네고사절";
        LocalDateTime created = LocalDateTime.now();

        ItemCreateRequestDto requestDto = ItemCreateRequestDto.builder()
                .member(member)
                .title(title)
                .categories(categories)
                .price(price)
                .spec(spec)
                .sold(false)
                .build();

        //when
        Long itemId = itemService.save(requestDto);

        //then
        List<Item> item = itemRepository.findAll();
        assertThat(item.get(0).getMember().getId()).isEqualTo(member.getId());
        assertThat(item.get(0).getTitle()).isEqualTo(title);
        assertThat(item.get(0).getCategories()).isEqualTo(categories);
        assertThat(item.get(0).getPrice()).isEqualTo(price);
        assertThat(item.get(0).getSpec()).isEqualTo(spec);
        assertThat(item.get(0).getSold()).isEqualTo(false);

        System.out.println(">>>>> created = " + item.get(1).getCreated());
        assertThat(item.get(0).getCreated()).isAfter(created);
    }

    @Test
    public void 상품수정(){
        //given
        //when
        //then
    }

    @Test
    public void 상품삭제(){
        //given
        //when
        //then
    }

    @Test
    public void 상품_하나_조회(){
        //given
        //when
        //then
    }

    @Test
    public void 상품_모두_조회(){
        //given
        //when
        //then
    }
}
