package cucumbermarket.cucumbermarketspring.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;
    private Address address;
    private String password;
    private String email;
    private LocalDateTime birthDate;
    private String contact;
    private int ratingScore;
    @OneToOne
    @JoinColumn(name = "favlist_id")
    private FavouriteList favouriteList;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public String getContact() {
        return contact;
    }

    public int getRatingScore() {
        return ratingScore;
    }

    public FavouriteList getFavouriteList() {
        return favouriteList;
    }
}
