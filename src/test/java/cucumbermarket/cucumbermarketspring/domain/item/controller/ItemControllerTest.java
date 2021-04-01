package cucumbermarket.cucumbermarketspring.domain.item.controller;

import cucumbermarket.cucumbermarketspring.domain.file.domain.File;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Categories;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import cucumbermarket.cucumbermarketspring.domain.item.domain.ItemRepository;
import cucumbermarket.cucumbermarketspring.domain.item.dto.ItemCreateRequestDto;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @After
    public void tearDown() throws Exception {
        itemRepository.deleteAll();
    }

    @Test
    public void 상품등록() throws Exception {
        //given
        Member member = getMember();
        String title = "급처분합니다.";
        Categories categories = Categories.CULTURAL;
        int price = 20000;
        File file = new File("data1", "data2", "data3");
        List<File> photoList = new ArrayList<>();
        photoList.add(file);
        String spec = "돈이 급해서 싸게 내놔요";

        ItemCreateRequestDto requestDto = ItemCreateRequestDto.builder()
                .member(member)
                .title(title)
                .categories(categories)
                .price(price)
                .photo(photoList)
                .spec(spec)
                .sold(Boolean.FALSE)
                .build();

        String url = "http://localhost:" + port + "/api/item";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(
                url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Item> all = itemRepository.findAll();
        assertThat(all.get(0).getMember()).isEqualTo(member);
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getCategories()).isEqualTo(categories);
        assertThat(all.get(0).getPrice()).isEqualTo(price);
     //   assertThat(all.get(0).getPhoto().get(0)).isEqualTo(photoList.get(0));
        assertThat(all.get(0).getSpec()).isEqualTo(spec);
        assertThat(all.get(0).getSold()).isEqualTo(Boolean.FALSE);

    }

    public Member getMember(){
        Member member = new Member(
                "홍길동",
                "sj4321",
                new Address(),
                LocalDate.now(),
                "hgd@gmail.com",
                "010-2222-2222",
                0,
                "USER");

        return member;
    }
}
