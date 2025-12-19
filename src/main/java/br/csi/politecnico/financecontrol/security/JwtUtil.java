package br.csi.politecnico.financecontrol.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private final Key key;
    private final Long expiration;

    public JwtUtil(
            @Value("${finance.jwt.secret}") String secret,
            @Value("${finance.jwt.expiration}") Long expiration
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    public String generateToken(String username) {
        return generateToken(username, Map.of());
    }

    public String generateToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return parseClaims(token).getBody().getSubject();
    }

    public String extractEmail(String token) {
        return (String) parseClaims(token).getBody().get("email");
    }

    public String extractUserUuid(String token) {
        return (String) parseClaims(token).getBody().get("uuid");
    }

    public String extractName(String token) {
        return (String) parseClaims(token).getBody().get("name");
    }

    public String extractRole(String token) {
        return (String) parseClaims(token).getBody().get("role");
    }

    public String extractUserCpf(String token) {
        return (String) parseClaims(token).getBody().get("cpf");
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
