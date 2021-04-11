package cucumbermarket.cucumbermarketspring.domain.member.service;

import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.MemberRepository;
import cucumbermarket.cucumbermarketspring.domain.member.dto.MemberProfileDto;
import cucumbermarket.cucumbermarketspring.domain.member.dto.UpdateMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
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
    public UserDetails loadUserByUsername (String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return new User(member.getEmail(), member.getPassword(), authorities);
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

    /**
     * 회원 프로필 조회
     */
    @Transactional
    public MemberProfileDto getMemberProfile(Long memberId) {
        try {
            Member member = memberRepository.getOne(memberId);
            MemberProfileDto memberProfileDto = new MemberProfileDto(member.getName(), member.getAddress(), member.getBirthdate(), member.getEmail(), member.getContact(), member.getRatingScore());
            return memberProfileDto;
        } catch (EntityNotFoundException e) {
            return new MemberProfileDto();
        }
    }

}

