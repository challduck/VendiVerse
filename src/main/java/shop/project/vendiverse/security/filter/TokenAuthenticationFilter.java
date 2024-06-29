package shop.project.vendiverse.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.project.vendiverse.exception.ExceptionCode;
import shop.project.vendiverse.exception.ExceptionResponse;
import shop.project.vendiverse.config.jwt.TokenProvider;
import shop.project.vendiverse.security.TokenService;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final TokenService tokenService;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HEADER_AUTHORIZATION);

        if (authHeader != null) {
            String token = getAccessToken(authHeader);
            if (tokenProvider.validateToken(token)) {
                if (tokenService.isAccessTokenBlocked(token)){
                    throw new ExceptionResponse(ExceptionCode.INVALID_TOKEN);
                }

                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String authHeader){
        if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)){
            return authHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
