package cucumbermarket.cucumbermarketspring.domain.member.dto;

import lombok.Data;

@Data
public class MemberDto {
    private Long id;
    private String name;
    private String email;
    private String contact;
}
