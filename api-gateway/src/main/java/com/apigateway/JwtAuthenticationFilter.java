package com.apigateway;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // ✅ PUBLIC APIs (no JWT needed)
        if (path.startsWith("/auth") ||
            path.startsWith("/user/email") ||
            path.startsWith("/user/all") ||
            path.startsWith("/review/order") ||
            path.startsWith("/notification")) {
            return chain.filter(exchange);
        }

        String header = exchange.getRequest()
                .getHeaders()
                .getFirst("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            return onError(exchange);
        }

        String token = header.substring(7);

        try {
            jwtUtil.validateToken(token);

            String email = jwtUtil.extractEmail(token);
            String role = jwtUtil.extractRole(token);

            // 🔥 ONLY FORWARD HEADERS (NO SPRING SECURITY)
            ServerHttpRequest request = exchange.getRequest().mutate()
                    .headers(headers -> {
                        headers.set("X-User-Email", email);
                        headers.set("X-User-Role", role);
                    })
                    .build();

            return chain.filter(exchange.mutate().request(request).build());

        } catch (Exception e) {
            return onError(exchange);
        }
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}