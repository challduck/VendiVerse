package shop.project.venver_gateway.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@Slf4j
public class JWTFilter extends AbstractGatewayFilterFactory<JWTFilter.Config> {

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    @Data
    public static class Config {
        private boolean preLogger;
        private boolean postLogger;
    }

    public JWTFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                return chain.filter(exchange);
            }

            String header = request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).getFirst();
            if (!header.startsWith("Bearer ")){
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            String token = header.substring(7);

            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(jwtSecretKey)
                        .parseClaimsJws(token)
                        .getBody();

                ServerHttpRequest.Builder mutatedRequest = request.mutate();
                for (Map.Entry<String, Object> entry : claims.entrySet()) {
                    String claimKey = "X-Header" + entry.getKey();
                    String claimValue = String.valueOf(entry.getValue());
                    mutatedRequest.header(claimKey, claimValue);
                }

                request = mutatedRequest.build();
                exchange = exchange.mutate().request(request).build();
            } catch (Exception e){
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            log.info("Custom PRE filter: request id -> {}", request.getId());
            log.info("Custom PRE filter: request uri -> {}", request.getURI());
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("Custom POST filter: response code -> {}", response.getStatusCode());
            }));
        };
    }
}