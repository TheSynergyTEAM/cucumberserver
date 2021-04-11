package cucumbermarket.cucumbermarketspring.domain.member.controller;

import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
import cucumbermarket.cucumbermarketspring.domain.member.dto.MemberDto;
import cucumbermarket.cucumbermarketspring.domain.member.dto.MemberProfileDto;
import cucumbermarket.cucumbermarketspring.domain.member.service.MemberDetailServiceImpl;
import cucumbermarket.cucumbermarketspring.domain.member.service.MemberService;
import cucumbermarket.cucumbermarketspring.exception.ForbiddenException;
import cucumbermarket.cucumbermarketspring.exception.NotCorrectPasswordException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private AuthenticationManager authenticationManager;
    private final MemberService memberService;
    private final MemberDetailServiceImpl memberDetailService;

    @GetMapping("/api/members")
    public List<Member> members() {
        return memberService.searchAllMember();
    }

    @GetMapping("/api/members/{id}")
    public MemberDto memberById(@PathVariable("id") Long id) {
        Member member = memberService.searchMemberById(id);
        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setName(member.getName());
        memberDto.setEmail(member.getEmail());
        memberDto.setContact(member.getContact());
        return memberDto;
    }

    /**
     *
     * 회원가입
     */
    @CrossOrigin
    @PostMapping("/api/members")
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request) {
        Address address = new Address(request.city, request.street1, request.street2, request.zipcode);
        LocalDate birthDate = LocalDate.parse(request.birthDate);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = bCryptPasswordEncoder.encode(request.password);
        Member member = new Member(request.name, hashedPassword, address, birthDate,request.email, request.contact, 0, "USER");
        Long id = memberService.createMember(member);
        return new CreateMemberResponse(id);
    }

    /**
     * 로그인
     */
    @CrossOrigin
    @PostMapping("/login")
    public LoginResponseDTO loginMember(@RequestBody @Valid LoginRequestDTO loginRequest) {
        System.out.println("HERE");
        String email = loginRequest.getEmail();
        System.out.println("email = " + email);
        UserDetails userDetails = memberDetailService.loadUserByUsername(email);
        String password = loginRequest.getPassword();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
            throw new NotCorrectPasswordException("비밀번호가 일치하지 않습니다");
        }
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.name = userDetails.getUsername();
        return loginResponseDTO;
    }

    /**
     * 로그아웃
     */
    @CrossOrigin
    @GetMapping("/logout")
    public void logoutMember(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("request = " + request + ", response = " + response);
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
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


    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
    @Data
    static class CreateMemberRequest{
        @NotEmpty
        private String name;
        private String password;
        private String city;
        private String street1;
        private String street2;
        private String zipcode;
        private String email;
        private String birthDate;
        private String contact;
    }

    @Data
    static class LoginRequestDTO {
        private String email;
        private String password;
    }

    @Data
    static class LoginResponseDTO {
        private String name;
    }

}
