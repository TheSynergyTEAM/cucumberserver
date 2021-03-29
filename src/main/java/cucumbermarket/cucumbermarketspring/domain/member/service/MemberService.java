package cucumbermarket.cucumbermarketspring.domain.member.service;

import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.MemberRepository;
import cucumbermarket.cucumbermarketspring.domain.member.UpdateMemberDto;
import cucumbermarket.cucumbermarketspring.domain.member.dto.MemberCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long createMember(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복회원 검사
     */
    private void validateDuplicateMember(Member member) {

        Member memberByEmail = memberRepository.findByEmail(member.getEmail());
        if (memberByEmail != null) {
            throw new IllegalStateException("중복 회원 존재");
        }
    }

    /**
     * 모든 회원 조회
     */
    @Transactional(readOnly = true)
    public List<Member> searchAllMember() {
        return memberRepository.findAll();
    }

    /**
     * 회원 조회
     */
    @Transactional(readOnly = true)
    public Member searchMemberById(Long memberId) {
        return memberRepository.getOne(memberId);
    }

    @Override
    public Member loadUserByUsername (String email) throws UsernameNotFoundException {
        try {
            Member byEmail = memberRepository.findByEmail(email);
            return byEmail;
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException((email));
        }


    }
    /**
     * 회원 수정
     */
    @Transactional
    public void updateMember(UpdateMemberDto updateMemberDto){
        Member one = memberRepository.getOne(updateMemberDto.getId());
        one.change(updateMemberDto);
    }

    /**
     * 회원 삭제
     */
    @Transactional
    public void deleteMember(Long memberId) {
        Member one = memberRepository.getOne(memberId);
        memberRepository.delete(one);

    }
}

