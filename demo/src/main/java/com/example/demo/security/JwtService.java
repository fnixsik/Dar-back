package com.example.demo.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.*;

@Service
public class JwtService {

    private static final String SECRET_KEY = "my-first-secret-key-for-dar";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    private final Key key;

    public JwtService(){
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Генерация токена
    public String generateToken(String username, Set<String> roles){
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Извлечение username
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Извлечение ролей
    @SuppressWarnings("unchecked")
    public Set<String> extractRoles(String token) {
        return new HashSet<>(
                (List<String>) extractAllClaims(token).get("roles")
        );
    }

    // Проверка валидности
    public boolean isTokenValid(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
