package cucumbermarket.cucumbermarketspring.domain.member;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import cucumbermarket.cucumbermarketspring.domain.chat.Message.Message;
import cucumbermarket.cucumbermarketspring.domain.favourite.FavouriteItem;
import cucumbermarket.cucumbermarketspring.domain.member.dto.UpdateMemberDto;
import cucumbermarket.cucumbermarketspring.domain.item.Item;
import cucumbermarket.cucumbermarketspring.domain.member.address.Address;
import cucumbermarket.cucumbermarketspring.domain.review.Review;
import lombok.*;
import org.hibernate.annotations.Proxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Entity
@Table(name = "member")
@Builder
@Proxy(lazy = false)
public class Member implements UserDetails {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", unique = true, nullable = false)
    private Long id;

    @Column(length = 15, nullable = false)
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

    @OneToMany(mappedBy = "member", cascade = CascadeType.MERGE, orphanRemoval = true)
    @JsonManagedReference
    private List<Item> item = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.MERGE, orphanRemoval = true)
    @JsonManagedReference("member")
    private List<FavouriteItem> favouriteItem = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.MERGE, orphanRemoval = true)
    @JsonManagedReference
    private List<Review> review = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<Message> messageList = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    //빌더
    @Builder
    public Member(String name, String password, Address address, LocalDate birthdate, String email, String contact, int ratingScore, String role) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.birthdate = birthdate;
        this.email = email;
        this.contact = contact;
        this.ratingScore = ratingScore;
        this.roles.add(role);
    }

    public Member() {

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
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return name;
    }

    //계정 만료 여부
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정 잠금 여부
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //패스워드 만료 여부
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}