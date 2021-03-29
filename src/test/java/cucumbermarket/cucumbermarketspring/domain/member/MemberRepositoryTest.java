package cucumbermarket.cucumbermarketspring.domain.member;

import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class MemberRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 회원_db_저장() throws Exception{
        //given
        String name = "당근당근";
        Address address = new Address();
        String password = "carrot82";
        LocalDate birthdate = LocalDate.of(2000, 8, 14);
        String email = "yujinsong.tech@gmail.com";
        String contact = "010-1004-1004";

        Member member = Member.builder()
                        .name(name)
                        .address(address)
                        .password(password)
                        .birthdate(birthdate)
                        .email(email)
                        .contact(contact)
                        .build();

        //when
        testEntityManager.persist(member);

        //then
        assertEquals(member, testEntityManager.find(Member.class, member.getId()));
    }
}
