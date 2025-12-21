package com.noteam.next.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(int id, String email) {
        Date currentDate = new Date();
        long expiration = currentDate.getTime() + jwtExpiration;
        Map<String, Object> body = new HashMap<>();
        body.put("id", id);
        body.put("email", email);
        return Jwts.builder()
                .setClaims(body)
                .setIssuedAt(currentDate)
                .setExpiration(new Date(expiration))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();
    }

    public int getIdFromToken(String token) {
        Claims value = getClaimsFromToken(token);
        int id = ((Number) value.get("id"))
                .intValue();
        return id;
    }

    public String getEmailFromToken(String token) {
        Claims value = getClaimsFromToken(token);
        String email = (String) value.get("email");
        return email;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key).build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }
}
