package cucumbermarket.cucumbermarketspring.domain.member.dto;

import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
import lombok.Data;

import javax.persistence.Embedded;
import java.time.LocalDate;

@Data
public class MemberProfileDto {
    private String name;
    private String city;
    private String street1;
    private LocalDate birthdate;
    private String email;
    private String contact;
    private Integer ratingScore;

    public MemberProfileDto(){}

    public MemberProfileDto(String name, Address address, LocalDate birthdate, String email, String contact, Integer ratingScore) {
        this.name = name;
        this.city = address.getCity();
        this.street1 = address.getStreet1();
        this.birthdate = birthdate;
        this.email = email;
        this.contact = contact;
        this.ratingScore = ratingScore;
    }
}
