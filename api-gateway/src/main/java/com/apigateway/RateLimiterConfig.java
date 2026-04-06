package com.apigateway;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimiterConfig {

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> {
            // Use IP address as rate limit key for unauthenticated requests
            // Use X-User-Email header (set by JwtAuthenticationFilter) if available
            String email = exchange.getRequest().getHeaders().getFirst("X-User-Email");
            if (email != null) {
                return Mono.just(email);
            }
            // Fallback to client IP
            return Mono.just(
                    exchange.getRequest().getRemoteAddress() != null
                            ? exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
                            : "anonymous"
            );
        };
    }
}
