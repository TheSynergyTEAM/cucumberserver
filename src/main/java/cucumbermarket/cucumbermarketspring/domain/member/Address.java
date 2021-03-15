package cucumbermarket.cucumbermarketspring.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {
    private String city;
    private String street1;
    private String street2;
    private String zipcode;
}
