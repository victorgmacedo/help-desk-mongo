package br.com.helpdesk.api.security.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {

    static final String CLAIM_KEY_USER = "sub";
    static final String CLAIM_KEY_CREATED = "created";
    static final String CLAIM_KEY_EXPIRED = "exp";

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${jwt.token}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    public String getUsernameFromToken(String token){
        try {
            final Claims claims = getClaimsFromToken(token);
            return claims.getSubject();
        }catch (Exception e){
            return null;
        }
    }

    public LocalDateTime getExpirationFromToken(String token){
        try {
            final Claims claims = getClaimsFromToken(token);
            return LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
        }catch (Exception e){
            return null;
        }
    }

    private Claims getClaimsFromToken(String token) {
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            return null;
        }
    }

    private boolean isTokeExpired(String token){
        LocalDateTime expiration = getExpirationFromToken(token);
        return expiration.isBefore(LocalDateTime.now());
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USER, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, LocalDateTime.now());

        return doGenerateToken(claims);
    }

    private String doGenerateToken(Map<String, Object> claims) {
        LocalDateTime createdDate = (LocalDateTime) claims.get(CLAIM_KEY_CREATED);
        LocalDateTime expirationDate = createdDate.plus(expiration, ChronoUnit.MINUTES);
        return Jwts.builder()
                .setClaims(claims)
                .serializeToJsonWith(new JacksonSerializer(this.objectMapper))
                .setExpiration(Date.from(expirationDate.atZone(ZoneId.systemDefault()).toInstant())).signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean canTokenBeRefreshed(String token){
        return (!isTokeExpired(token));
    }

    public String refreshToken(String token){
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put(CLAIM_KEY_CREATED, LocalDateTime.now());
            return doGenerateToken(claims);
        }catch (Exception e){
            return null;
        }
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        JwtUser user = (JwtUser) userDetails;
        String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokeExpired(token));
    }


}
