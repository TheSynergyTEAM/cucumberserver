package cucumbermarket.cucumbermarketspring.domain.member;

import cucumbermarket.cucumbermarketspring.domain.favourite.favouritelist.FavouriteList;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "member")
public class Member {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 15, nullable = false, unique = true)
    private String name;

    @Column(length = 30, nullable = false)
    private String password;

    @Embedded
    private Address address;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String contact;

    @Column(columnDefinition = "")
    private int ratingScore;

    @OneToOne(mappedBy = "member")
    private FavouriteList favouriteList;


    //빌더
    @Builder
    public Member(String name, String password, Address address, LocalDate birthdate, String email, String contact, int ratingScore){
        this.name = name;
        this.password = password;
        this.address = address;
        this.birthdate = birthdate;
        this.email = email;
        this.contact = contact;
        this.ratingScore = ratingScore;
    }
}