package cucumbermarket.cucumbermarketspring.domain.member.address;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Address {
    private String state;
    private String city;
    private String street1;
    private String street2;
    private String zipcode;

    @Builder
    public Address(String state, String city, String street1, String street2, String zipcode){
        this.state = state;
        this.city = city;
        this.street1 = street1;
        this.street2 = street2;
        this.zipcode = zipcode;
    }
}
