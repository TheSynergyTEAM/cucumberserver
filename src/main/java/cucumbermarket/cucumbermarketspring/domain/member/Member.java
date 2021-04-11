package cucumbermarket.cucumbermarketspring.domain.member;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
import cucumbermarket.cucumbermarketspring.domain.favourite.domain.FavouriteItem;
import cucumbermarket.cucumbermarketspring.domain.member.dto.UpdateMemberDto;
import cucumbermarket.cucumbermarketspring.domain.item.domain.Item;
import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
import cucumbermarket.cucumbermarketspring.domain.review.domain.Review;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "member")
public class Member implements UserDetails {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", unique = true, nullable = false)
    private Long id;

    @Column(length = 15, nullable = false, unique = true)
    private String name;

    @Column(length = 100, nullable = false)
    private String password;

    @Embedded
    private Address address;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String contact;

    @Column(columnDefinition = "int default 0")
    private int ratingScore;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<Item> item = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavouriteItem> favouriteItem = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<Message> messageList = new ArrayList<>();

    @Column(name = "auth")
    private String auth;

    //빌더
    @Builder
    public Member(String name, String password, Address address, LocalDate birthdate, String email, String contact, int ratingScore, String auth) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.birthdate = birthdate;
        this.email = email;
        this.contact = contact;
        this.ratingScore = ratingScore;
        this.auth = auth;
    }

    /**
     * 수정
     */
    public void change(UpdateMemberDto updateMemberDto) {
        this.id = updateMemberDto.getId();
        this.name = updateMemberDto.getName();
        this.password = updateMemberDto.getPassword();
        this.address = updateMemberDto.getAddress();
        this.birthdate = updateMemberDto.getBirthdate();
        this.email = updateMemberDto.getEmail();
        this.contact = updateMemberDto.getContact();
    }

    // 사용자 권한 콜렉션 형태로 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    @Override
    public String getUsername() {
        return name;
    }
    //계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //계정 잠금 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //패스워드 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}