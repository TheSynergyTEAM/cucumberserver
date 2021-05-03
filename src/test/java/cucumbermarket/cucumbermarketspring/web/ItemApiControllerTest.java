package cucumbermarket.cucumbermarketspring.web;

import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.item.ItemRepository;
import cucumbermarket.cucumbermarketspring.domain.item.category.Categories;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.MemberRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ItemApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @After
    public void tearDown() throws Exception{
        itemRepository.deleteAll();
    }

    @Test
    public void 중고상품_등록() throws Exception{
        //given
        List<Member> memberList = memberRepository.findAll();
        Member member = memberList.get(0);
        String title = "아기용품 싸게 팝니다.";
        Categories categories = Categories.KID;
        int price = 10000;
        String spec = "잔기스 있어요. 감안 부탁드립니다";
        LocalDateTime created = LocalDateTime.now();

        ItemCreateRequestDto requestDto = ItemCreateRequestDto.builder()
                .member(member)
                .title(title)
                .categories(categories)
                .price(price)
                .spec(spec)
                .build();

        String url = "http://localhost:" + port + "/item";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Item> all = itemRepository.findAll();
        assertThat(all.get(0).getMember()).isEqualTo(member);
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getCategories()).isEqualTo(categories);
        assertThat(all.get(0).getPrice()).isEqualTo(price);
        assertThat(all.get(0).getSpec()).isEqualTo(spec);

        System.out.println(">>>>> created = " + all.get(0).getCreated());
        assertThat(all.get(0).getCreated()).isAfter(created);
    }

    @Test
    public void 중고상품_수정() throws Exception{
        //given
        //when
        //then
    }

    @Test
    public void 중고상품_삭제() throws Exception {
        //given
        //when
        //then
    }

    @Test
    public void 상품_하나_조회() throws Exception{
        //given
        //when
        //then
    }

    @Test
    public void 상품_전부_조회() throws Exception{
        //given
        //when
        //then
    }

    @Test
    public void 키워드로_조회() throws Exception{
        //given
        //when
        //then
    }
}
