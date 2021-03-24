package cucumbermarket.cucumbermarketspring.security;

import cucumbermarket.cucumbermarketspring.domain.member.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

@Getter @Setter
public class SecurityMember extends User {

    private Long id;
    private String registeredName;
    public SecurityMember(Member member) {
        super(member.getUsername(), member.getPassword(), member.getAuthorities());
        this.id = member.getId();
        this.registeredName = member.getName();
    }
}
