package cucumbermarket.cucumbermarketspring.domain.member.controller;

import cucumbermarket.cucumbermarketspring.domain.member.Address;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.MemberDto;
import cucumbermarket.cucumbermarketspring.domain.member.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

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

    @PostMapping("/api/members")
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request) {
        Address address = new Address(request.city, request.street1, request.street2, request.zipcode);
        LocalDate birthDate = LocalDate.parse(request.birthDate);
        Member member = new Member(request.name, request.password, address, birthDate,request.email, request.contact, 0);
        Long id = memberService.createMember(member);
        return new CreateMemberResponse(id);
    }


    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id=id;
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
}
