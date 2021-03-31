package cucumbermarket.cucumbermarketspring.domain.member;

import lombok.Data;

@Data
public class MemberDto {
    private Long id;
    private String name;
    private String email;
    private String contact;
}
