package cucumbermarket.cucumbermarketspring.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationTokenProvider implements AuthenticationTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenProvider.class);
    private static final String SECRET_KEY = "zdtlD3JK56m6wTTgsNFhqzjqPjiwehqroewqooisauozcxkcxnzoihaowehr";
    private static final long EXPIRATION_MS = 1000 * 60 * 60 * 12;
//    private static final long EXPIRATION_MS = 1000 * 10;

    @Override
    public String parseTokenString(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null ) {
            return bearerToken;
        }
        return null;
    }

    @Override
    public AuthenticationToken issue(Long userNo) {
        return JwtAuthenticationToken.builder().token(buildToken(userNo)).build();
    }

    // Create JWT Token
    private String buildToken(Long userNo) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plus(EXPIRATION_MS, ChronoUnit.MILLIS);
        return Jwts.builder()
                .setSubject(String.valueOf(userNo))
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    @Override
    public Long getTokenOwnerId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    @Override
    public boolean validateToken(String token) throws ExpiredJwtException{
        if (isNotEmpty(token)) {
            try {
                Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
                return true;
            } catch (SignatureException e) {
                logger.error("Invalid JWT signature", e);
            } catch (MalformedJwtException e) {
                logger.error("Invalid JWT Token", e);
            } catch (ExpiredJwtException e) {
                logger.error("Expired JWT Token", e);
                throw e;
            } catch (UnsupportedJwtException e) {
                logger.warn("Unsupported JWT token", e);
            } catch (IllegalArgumentException e) {
                logger.error("JWT claims string is empty", e);
            }
        }
        return false;
    }
}
