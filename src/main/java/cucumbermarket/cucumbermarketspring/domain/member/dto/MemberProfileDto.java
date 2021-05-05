package cucumbermarket.cucumbermarketspring.domain.member.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberProfileDto {

    private String name;
    private String city;
    private String street1;
    private LocalDate birthdate;
    private String email;
    private String contact;
    private Integer ratingscore;

    public MemberProfileDto() {
    }

    public MemberProfileDto(String name, String city, String street1, LocalDate birthdate, String email, String contact, Integer ratingscore) {
        this.name = name;
        this.city = city;
        this.street1 = street1;
        this.birthdate = birthdate;
        this.email = email;
        this.contact = contact;
        this.ratingscore = ratingscore;
    }
}
