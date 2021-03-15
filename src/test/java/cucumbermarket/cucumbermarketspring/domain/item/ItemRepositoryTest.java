package cucumbermarket.cucumbermarketspring.domain.item;

import cucumbermarket.cucumbermarketspring.domain.member.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ItemRepositoryTest {
 //   @Autowired
 //   private TestEntityManager testEntityManager;

 //   @Autowired
 //   private ItemRepository memberRepository;

 //   @Test
 //   public void 물품내역_db_저장() throws Exception{
        //given

        //when


        //then

}
