package cucumbermarket.cucumbermarketspring.domain.member.controller;

import cucumbermarket.cucumbermarketspring.domain.chat.Message.service.MessageService;
import cucumbermarket.cucumbermarketspring.domain.chat.chatroom.service.ChatRoomService;
import cucumbermarket.cucumbermarketspring.domain.member.MemberRepository;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
import cucumbermarket.cucumbermarketspring.domain.member.avatar.storage.StorageService;
import cucumbermarket.cucumbermarketspring.domain.member.dto.LoginResponseDto;
import cucumbermarket.cucumbermarketspring.domain.member.dto.MemberProfileDto;
import cucumbermarket.cucumbermarketspring.domain.member.dto.UpdateMemberDto;
import cucumbermarket.cucumbermarketspring.domain.member.service.MemberService;
import cucumbermarket.cucumbermarketspring.exception.ForbiddenException;
import cucumbermarket.cucumbermarketspring.security.JwtAuthenticationTokenProvider;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MessageService messageService;
    private final MemberRepository memberRepository;
    private final ChatRoomService chatRoomService;
    private final StorageService storageService;
    private final JwtAuthenticationTokenProvider jwtAuthenticationTokenProvider;

    @CrossOrigin
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader Map<String, String> headers) {
        String token = headers.get("authorization");
        Long tokenOwnerId = jwtAuthenticationTokenProvider.getTokenOwnerId(token);
        if (tokenOwnerId > 0) {
            Member member = memberRepository.getOne(tokenOwnerId);
            LoginResponseDto loginResponseDTO = getLoginResponseDTO(member);
            if (messageService.countNewMessages(member.getId()) > 0) {
                loginResponseDTO.setUnread(Boolean.TRUE);
            }
            return ResponseEntity.ok().header(
                    HttpHeaders.AUTHORIZATION,token
            ).body(
                    loginResponseDTO
            );
        }
        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.NOT_FOUND);
    }

    /**
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
    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@RequestBody @Valid LoginRequestDTO loginRequest) {
        String email = loginRequest.getEmail();
        UserDetails userDetails = memberService.loadUserByUsername(email);
        String password = loginRequest.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
            return ResponseEntity.badRequest().body("Password is not correct");
        }

        Member member = memberRepository.findByEmail(email);
        LoginResponseDto loginResponseDTO = getLoginResponseDTO(member);
        if (messageService.countNewMessages(member.getId()) > 0) {
            loginResponseDTO.setUnread(Boolean.TRUE);
        }
        String token = jwtAuthenticationTokenProvider.issue(member.getId()).getToken();
        HttpHeaders responseheaders = new HttpHeaders();
        responseheaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        responseheaders.add("Authorization", token);

        return ResponseEntity.ok()
                .headers(responseheaders)
                .body(loginResponseDTO);
    }

    private LoginResponseDto getLoginResponseDTO(Member member) {
        LoginResponseDto responseDto = new LoginResponseDto(
                member.getId(),
                member.getName(),
                member.getAddress(),
                member.getBirthdate(),
                member.getEmail(),
                member.getContact(),
                member.getRatingScore()
        );
        responseDto.setAvatar(storageService.getAvatarPath(member.getId()));
        return responseDto;
    }

    /**
     * 프로필 조회
     */
    @CrossOrigin
    @GetMapping("/member/{id}")
    public MemberProfileDto getMemberProfile(@PathVariable("id") Long id) {
        MemberProfileDto memberProfile = memberService.getMemberProfile(id);
        if (memberProfile.getName() == null) {
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
        UpdateMemberDto updateMember = memberService.updateMember(request);
        return ResponseEntity.ok()
                .body(updateMember);
    }

    /**
     * 회원 탈퇴
     */
    @CrossOrigin
    @DeleteMapping("/member/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
        chatRoomService.updateValidByDeletedMember(id);
        return ResponseEntity.ok().body("OK");
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
