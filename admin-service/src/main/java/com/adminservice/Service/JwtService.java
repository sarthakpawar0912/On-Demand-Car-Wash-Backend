package com.adminservice.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

@RefreshScope
@Service
public class JwtService {

    private final String SECRET = "mysupersecretkeymysupersecretkey12345";

    public Claims extractAllClaims(String token) {
        Key key = new SecretKeySpec(SECRET.getBytes(), "HmacSHA256");

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }
}