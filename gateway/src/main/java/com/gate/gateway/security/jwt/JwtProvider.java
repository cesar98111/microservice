package com.gate.gateway.security.jwt;

import com.gate.gateway.user.entity.User;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtProvider {
    public static final String TOKEN_TYPE ="JWT";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIC = "Bearer";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.duration}")
    private int jwtLifeInDays;

    private JwtParser jwtParser;

    private SecretKey secretKey;

    @PostConstruct
    public void init(){
        secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        jwtParser = Jwts.parser().setSigningKey(secretKey).build();

    }

    public String generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Date tokenExpirationDateTime = Date.from(
                LocalDateTime.now().plusDays(jwtLifeInDays)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );

        return Jwts.builder()
                .setHeaderParam("typ", TOKEN_TYPE)
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(tokenExpirationDateTime)
                .signWith(secretKey)
                .compact();
    }


}
