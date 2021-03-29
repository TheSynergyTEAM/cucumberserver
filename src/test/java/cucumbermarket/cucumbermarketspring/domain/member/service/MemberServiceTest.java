package cucumbermarket.cucumbermarketspring.domain.member.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

  //  @Autowired
  //  MemberService memberService;

  //  @Autowired
  //  MemberRepository memberRepository;

  //  @Autowired
  //  EntityManager entityManager;

    @Test
    public void createMemberTest() {
        //given
       // Member member = new Member("구형준", "1234", new Address(), LocalDate.now(), "abc@def.com", "010-1234-5678", 5);

        //when
    //    Long member1 = memberService.createMember(member);
    //    entityManager.flush();
        //then
    //    assertEquals(member, memberRepository.getOne(member1));

    }

    @Test
    public void duplicateMemberTest() throws Exception {

        //given
    //    Member member1 = new Member("구형준1", "1234", new Address(), LocalDate.now(), "abc@def.com", "010-1234-5678", 5);
    //    Member member2 = new Member("구형준2", "1234", new Address(), LocalDate.now(), "abc@def.com", "010-1234-5678", 5);
        //when
    //    Long member1Id = memberService.createMember(member1);
    //    entityManager.persist(member1);
    //    try {
    //        memberService.createMember(member2);
    //    } catch (IllegalStateException e) {
    //        return;
     //   }
        //then
    //    fail("Sould error be occured");

    }
}