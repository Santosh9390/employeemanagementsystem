package com.santosh.employeemanagement.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
@Component
public class JwtUtil {

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        // Secure key generation (>= 256-bit for HS256)
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
    // Generate a JWT token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour validity
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Extract username from JWT token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Extract all claims (payload) from JWT token
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // Validate JWT token
    public boolean validateToken(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
