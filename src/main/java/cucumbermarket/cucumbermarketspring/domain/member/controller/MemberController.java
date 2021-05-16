package cucumbermarket.cucumbermarketspring.domain.member.controller;

import cucumbermarket.cucumbermarketspring.domain.member.MemberRepository;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
import cucumbermarket.cucumbermarketspring.domain.member.dto.LoginResponseDto;
import cucumbermarket.cucumbermarketspring.domain.member.dto.MemberDto;
//import cucumbermarket.cucumbermarketspring.domain.member.dto.MemberProfileDto;
import cucumbermarket.cucumbermarketspring.domain.member.dto.MemberProfileDto;
import cucumbermarket.cucumbermarketspring.domain.member.dto.UpdateMemberDto;
import cucumbermarket.cucumbermarketspring.domain.member.service.MemberService;
import cucumbermarket.cucumbermarketspring.exception.ForbiddenException;
import cucumbermarket.cucumbermarketspring.exception.NotCorrectPasswordException;
import cucumbermarket.cucumbermarketspring.security.JwtAuthenticationTokenProvider;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtAuthenticationTokenProvider jwtAuthenticationTokenProvider;
    private final AuthenticationManager authenticationManager;

    /**
     *
     * 회원가입
     */
    @CrossOrigin
    @PostMapping("/member")
    public ResponseEntity<?> saveMember(@RequestBody @Valid CreateMemberRequestDto request) {
        try {
            Address address = new Address(request.city, request.street1, request.street2, request.zipcode);
            LocalDate birthDate = LocalDate.parse(request.birthdate);
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = bCryptPasswordEncoder.encode(request.password);
            Member member = new Member(request.name, hashedPassword, address, birthDate, request.email, request.contact, 0, "USER");

            Long id = memberService.createMember(member);
            return ResponseEntity.ok().body(new CreateMemberResponseDto(id));
        } catch (NullPointerException e){
            return ResponseEntity.badRequest().body("Contents name is not correct");
        }
    }

    /**
     * 로그인
     */
    @CrossOrigin(exposedHeaders = {"Authorization"})
    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@RequestBody @Valid LoginRequestDTO loginRequest) {
        String email = loginRequest.getEmail();
        UserDetails userDetails = memberService.loadUserByUsername(email);
        String password = loginRequest.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
            return ResponseEntity.badRequest().body("Password is not correct");
        }

        Member memberbyEmail = memberRepository.findByEmail(email);
        Long userId = memberbyEmail.getId();
        LoginResponseDto loginResponseDTO = new LoginResponseDto(
                userId,
                memberbyEmail.getName(),
                memberbyEmail.getAddress(),
                memberbyEmail.getBirthdate(),
                memberbyEmail.getEmail(),
                memberbyEmail.getContact(),
                memberbyEmail.getRatingScore()
        );

        String token = jwtAuthenticationTokenProvider.issue(userId).getToken();
        System.out.println("token = " + token);
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        token
                        )
                .body(loginResponseDTO);
    }

    /**
     * 프로필 조회
     */
    @CrossOrigin
    @GetMapping("/member/{id}")
    public MemberProfileDto getMemberProfile(@PathVariable("id") Long id) {
        MemberProfileDto memberProfile = memberService.getMemberProfile(id);
        System.out.println("memberProfile = " + memberProfile);
        if (memberProfile.getName() == null) {
            System.out.println("here");
            throw new ForbiddenException();
        }
        return memberProfile;
    }

    /**
     * 회원 정보 수정
     */
    @CrossOrigin
    @PatchMapping("/member/{id}")
    public ResponseEntity<UpdateMemberDto> updateMember(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberDto request) {

        Member member = memberRepository.getOne(id);
        UpdateMemberDto updateMember = memberService.updateMember(request);
        return ResponseEntity.ok()
                .body(updateMember);
    }

    @Data
    static class CreateMemberResponseDto {
        private Long id;

        public CreateMemberResponseDto(Long id) {
            this.id = id;
        }
    }
    @Data
    static class CreateMemberRequestDto{
        @NotEmpty
        private String name;
        private String password;
        private String city;
        private String street1;
        private String street2;
        private String zipcode;
        private String email;
        private String birthdate;
        private String contact;
    }

    @Data
    static class LoginRequestDTO {
        private String email;
        private String password;
    }

}
