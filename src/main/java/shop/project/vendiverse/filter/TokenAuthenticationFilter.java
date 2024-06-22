package shop.project.vendiverse.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.project.vendiverse.config.jwt.TokenProvider;
import shop.project.vendiverse.domain.User;
import shop.project.vendiverse.service.TokenService;
import shop.project.vendiverse.service.UserService;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final UserService userService;
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
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), "로그인 후 다시 시도해 주세요.");
                    return;
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
