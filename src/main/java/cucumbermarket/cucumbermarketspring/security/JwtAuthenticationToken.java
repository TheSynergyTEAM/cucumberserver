package cucumbermarket.cucumbermarketspring.security;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtAuthenticationToken implements AuthenticationToken{
    private final String token;


}
