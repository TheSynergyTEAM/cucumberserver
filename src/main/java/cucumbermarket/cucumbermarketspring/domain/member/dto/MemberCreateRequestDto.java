package cucumbermarket.cucumbermarketspring.domain.member.dto;

import cucumbermarket.cucumbermarketspring.domain.member.Address;
import cucumbermarket.cucumbermarketspring.domain.member.Member;
import lombok.Builder;

import java.time.LocalDate;

public class MemberCreateRequestDto {
    private String name;
    private Address address;
    private String password;
    private LocalDate birthdate;
    private String email;
    private String contact;

    @Builder
    public MemberCreateRequestDto(String name, Address address, String password, LocalDate birthdate, String email, String contact){
        this.name = name;
        this.address = address;
        this.password = password;
        this.birthdate = birthdate;
        this.email = email;
        this.contact = contact;
    }

    public Member toEntity(){
        return Member.builder()
                .name(name)
                .address(address)
                .password(password)
                .birthdate(birthdate)
                .email(email)
                .contact(contact)
                .build();
    }
}
