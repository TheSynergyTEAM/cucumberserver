package cucumbermarket.cucumbermarketspring.domain.member.service;

import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.MemberRepository;
import cucumbermarket.cucumbermarketspring.domain.member.dto.MemberProfileDto;

import cucumbermarket.cucumbermarketspring.domain.member.dto.UpdateMemberDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    public void createMemberTest() {
        //given
        Member member = getMember(new Address(), "구형준");

        //when
        Long member1 = memberService.createMember(member);
        entityManager.flush();
        //then
        assertEquals(member, memberRepository.getOne(member1));

    }

    @Test
    public void duplicateMemberTest() throws Exception {

        //given
        Member member1 = getMember(new Address(), "구형준1");
        Member member2 = getMember(new Address(), "구형준2");
        //when
        Long member1Id = memberService.createMember(member1);
        entityManager.persist(member1);
        try {
            memberService.createMember(member2);
        } catch (IllegalStateException e) {
            return;
        }
        //then
        fail("Should error be occurred");
    }

    @Test
    public void profileTest() throws Exception {

        Address address = getAddress();
        //given
        Member member1 = getMember(address, "구형준1");
        entityManager.persist(member1);

        //when
        MemberProfileDto memberProfile = memberService.getMemberProfile(10L);
        System.out.println("memberProfile = " + memberProfile.getCity());
        //then
    }

    private Address getAddress() {
        return new Address("서울", "123", "123", "123");
    }

    private Member getMember(Address address, String name) {
        return new Member(name, "1234", address, LocalDate.now(), "abc@def.com", "010-1234-5678", 5, "USER");
    }
}