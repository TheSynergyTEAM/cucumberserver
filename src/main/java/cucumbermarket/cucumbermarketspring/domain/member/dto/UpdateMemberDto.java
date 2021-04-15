package cucumbermarket.cucumbermarketspring.domain.member.dto;

import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
import lombok.Data;

import javax.persistence.Embedded;
import java.time.LocalDate;

@Data
public class UpdateMemberDto {
    private Long id;
    private String name;
    private String city;
    private String street1;
    private String street2;
    private String zipcode;
    private String email;
    private String contact;
}
