package com.example.Investor.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Data
public class JwtTokenProvider {
    @Value("${app.jwt.secret-key-string}")
    private String secretKeyString;
    @Value("${app.jwt.expiration-time}")
    private long expirationTime;

    private SecretKey SECRET_KEY;
    @PostConstruct
    public void init(){
        this.SECRET_KEY= Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }
    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SECRET_KEY,Jwts.SIG.HS256)
                .compact();
    }
    public Boolean validateToken(String token,UserDetails userDetails){
        return extractUsername(token).equals(userDetails.getUsername());
    }
    public String extractUsername(String token){
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }


}
