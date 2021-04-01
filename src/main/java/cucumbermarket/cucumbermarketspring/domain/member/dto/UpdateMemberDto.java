package cucumbermarket.cucumbermarketspring.domain.member.dto;

import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
import lombok.Data;

import javax.persistence.Embedded;
import java.time.LocalDate;

@Data
public class UpdateMemberDto {
    private Long id;
    private String name;
    private String password;
    @Embedded
    private Address address;
    private LocalDate birthdate;
    private String email;
    private String contact;
}
