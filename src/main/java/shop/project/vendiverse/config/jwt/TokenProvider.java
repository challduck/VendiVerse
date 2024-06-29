package shop.project.vendiverse.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import shop.project.vendiverse.security.UserDetailsImpl;
import shop.project.vendiverse.security.UserDetailsServiceImpl;
import shop.project.vendiverse.user.entity.User;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final JwtProperties jwtProperties;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public String generateToken(User user, Duration duration, UserDetailsImpl userDetails) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + duration.toMillis()), user, userDetails);
    }

    private String makeToken(Date expiry, User user, UserDetailsImpl userDetails) {
        Date now = new Date();

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        List<String> authorityStrings = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .claim("authorities", authorityStrings)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaim(token);

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(claims.getSubject());

        Collection<? extends GrantedAuthority> authorities = getAuthorities(claims);

        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Claims claims) {
        List<String> authorities = claims.get("authorities", List.class);
        if (authorities == null || authorities.isEmpty()) {
            return Collections.emptyList();
        }
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiration.before(new Date());
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = getClaim(token);
        return claims.get("id", Long.class);
    }

    private Claims getClaim(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
