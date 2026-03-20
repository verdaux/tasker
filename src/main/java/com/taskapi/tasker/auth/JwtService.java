package com.taskapi.tasker.auth;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service 

public class JwtService { 

 

    private final SecretKey key; 

    private final long expiryMs; 

 

    public JwtService( 

            @Value("${app.jwt.secret:local-dev-secret-32-characters-ok}") String secret, 

            @Value("${app.jwt.expiry-ms:86400000}") long expiryMs) { 

        this.key = Keys.hmacShaKeyFor(secret.getBytes()); 

        this.expiryMs = expiryMs; 

    } 

 

    public String generate(UserDetails user) { 

        return Jwts.builder() 

                .subject(user.getUsername()) 

                .issuedAt(new Date()) 

                .expiration(new Date(System.currentTimeMillis() + expiryMs)) 

                .signWith(key) 

                .compact(); 

    } 

 

    public String extractUsername(String token) { 

        return claims(token).getSubject(); 

    } 

 

    public boolean isValid(String token, UserDetails user) { 

        return extractUsername(token).equals(user.getUsername()) 

                && !claims(token).getExpiration().before(new Date()); 

    } 

 

    private Claims claims(String token) { 

        return Jwts.parser().verifyWith(key).build() 

                .parseSignedClaims(token).getPayload(); 

    } 

} 