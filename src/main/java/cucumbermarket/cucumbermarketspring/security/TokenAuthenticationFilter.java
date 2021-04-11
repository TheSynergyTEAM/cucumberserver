package cucumbermarket.cucumbermarketspring.security;

import cucumbermarket.cucumbermarketspring.domain.member.Member;
import cucumbermarket.cucumbermarketspring.domain.member.MemberRepository;
import cucumbermarket.cucumbermarketspring.domain.member.service.MemberService;
import cucumbermarket.cucumbermarketspring.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationTokenProvider authenticationTokenProvider;

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = authenticationTokenProvider.parseTokenString(request);
        if (authenticationTokenProvider.validateToken(token)) {
            Long tokenOwnerId = authenticationTokenProvider.getTokenOwnerId(token);
            try {
                Member memberByTokenId = memberRepository.getOne(tokenOwnerId);
                User member = (User) memberService.loadUserByUsername(memberByTokenId.getEmail());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(member,"", member.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (UsernameNotFoundException e) {
                throw new ForbiddenException();
            }
        }
        filterChain.doFilter(request, response);

    }
}
