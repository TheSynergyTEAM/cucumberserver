package cucumbermarket.cucumbermarketspring.domain.member.service;

import cucumbermarket.cucumbermarketspring.domain.member.MemberRepository;
import cucumbermarket.cucumbermarketspring.domain.member.dto.MemberCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberCreateRequestDto requestDto){
        return memberRepository.save(requestDto.toEntity()).getId();
    }
}
