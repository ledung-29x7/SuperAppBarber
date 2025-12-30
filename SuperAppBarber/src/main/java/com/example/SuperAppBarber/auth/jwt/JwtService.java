package com.example.SuperAppBarber.auth.jwt;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    private static final String SECRET = "L618gmd1vItHhBxNgUuVbU2zoCf+jxwRY3xOYJ8q4lo7Qn263zLgag/3+htEthZbr4n0nlA6O1Lh6kyh4cUVQA==";

    private static final long ACCESS_EXPIRE = 1000L * 60 * 30; // 30 phút
    private static final long REFRESH_EXPIRE = 1000L * 60 * 60 * 24 * 14; // 14 ngày

    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // ===================== GENERATE =====================

    public String generateAccessToken(UUID userId, String role) {
        return Jwts.builder()
                .subject(userId.toString())
                .claim("role", role)
                .claim("type", "ACCESS")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRE))
                .signWith(signingKey()) // API mới
                .compact();
    }

    public String generateRefreshToken(UUID userId) {
        return Jwts.builder()
                .subject(userId.toString())
                .claim("type", "REFRESH")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRE))
                .signWith(signingKey())
                .compact();
    }

    // ===================== EXTRACT =====================

    public UUID extractUserId(String token) {
        return UUID.fromString(getClaims(token).getSubject());
    }

    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public String extractTokenType(String token) {
        return getClaims(token).get("type", String.class);
    }

    // ===================== VALIDATE =====================

    public boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // ===================== CORE =====================

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey()) // THAY setSigningKey
                .build()
                .parseSignedClaims(token) // THAY parseClaimsJws
                .getPayload(); // THAY getBody
    }
}
