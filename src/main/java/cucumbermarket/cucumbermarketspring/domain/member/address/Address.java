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

<<<<<<< HEAD:src/main/java/cucumbermarket/cucumbermarketspring/domain/member/address/Address.java
    public Address(String city, String street1, String street2, String zipcode) {
=======
    @Builder
    public Address(String state, String city, String street1, String street2, String zipcode){
        this.state = state;
>>>>>>> 542b833f949a4a026ad951075ec1e42e9cb04c76:src/main/java/cucumbermarket/cucumbermarketspring/domain/member/Address.java
        this.city = city;
        this.street1 = street1;
        this.street2 = street2;
        this.zipcode = zipcode;
    }
<<<<<<< HEAD:src/main/java/cucumbermarket/cucumbermarketspring/domain/member/address/Address.java
=======

>>>>>>> 542b833f949a4a026ad951075ec1e42e9cb04c76:src/main/java/cucumbermarket/cucumbermarketspring/domain/member/Address.java
}
