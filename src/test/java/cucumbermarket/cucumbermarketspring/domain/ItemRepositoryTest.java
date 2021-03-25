package cucumbermarket.cucumbermarketspring.domain;

import cucumbermarket.cucumbermarketspring.domain.item.domain.Categories;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import cucumbermarket.cucumbermarketspring.domain.item.domain.ItemRepository;
import cucumbermarket.cucumbermarketspring.domain.member.Address;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.MemberRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @After
    public void cleanup(){
        itemRepository.deleteAll();
    }

    @BeforeEach
    public void createMember(){
        String name = "홍길동";
        String password = "1234";
        Address address = new Address("충청북도", "청주시", "흥덕구", "수곡동", "123456");
        LocalDate birthdate = LocalDate.of(2001, 10, 7);
        String email = "hkd@gmail.com";
        String contact = "010-4199-3000";

        memberRepository.save(Member.builder()
                .name(name)
                .password(password)
                .address(address)
                .birthdate(birthdate)
                .email(email)
                .contact(contact)
                .ratingScore(0)
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

        itemRepository.save(Item.builder()
                .member(member)
                .title(title)
                .categories(categories)
                .price(price)
                .spec(spec)
                .sold(false)
                .build());

        //when
        List<Item> itemList = itemRepository.findAll();

        //then
        Item item = itemList.get(0);
        assertThat(item.getMember()).isEqualTo(member);
        assertThat(item.getTitle()).isEqualTo(title);
        assertThat(item.getCategories()).isEqualTo(categories);
        assertThat(item.getPrice()).isEqualTo(price);
        assertThat(item.getSpec()).isEqualTo(spec);
        assertThat(item.getSold()).isEqualTo(false);

        System.out.println(">>>>> created = " + item.getCreated());
        assertThat(item.getCreated()).isAfter(created);
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
