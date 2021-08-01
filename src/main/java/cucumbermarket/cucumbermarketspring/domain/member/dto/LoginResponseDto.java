package cucumbermarket.cucumbermarketspring.domain.member.dto;

import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
import lombok.Data;

import javax.persistence.Embedded;
import java.time.LocalDate;

@Data
public class LoginResponseDto {
    private Long id;
    private String name;
    private String city;
    private String street1;
    private String street2;
    private LocalDate birthdate;
    private String email;
    private String contact;
    private Integer ratingscore;
    private Boolean unread;
    private String avatar;

    public LoginResponseDto(){}

    public LoginResponseDto(Long id, String name, Address address, LocalDate birthdate, String email, String contact, Integer ratingscore) {
        this.id = id;
        this.name = name;
        this.city = address.getCity();
        this.street1 = address.getStreet1();
        this.street2 = address.getStreet2();
        this.birthdate = birthdate;
        this.email = email;
        this.contact = contact;
        this.ratingscore = ratingscore;
        this.unread = Boolean.FALSE;
    }
}
