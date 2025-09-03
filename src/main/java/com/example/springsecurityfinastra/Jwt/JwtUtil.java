package com.example.springsecurityfinastra.Jwt;

import com.example.springsecurityfinastra.entity.Employee;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${spring.jpa.properties.jwt.secret}")
    String secret;

    private SecretKey geyKey(){
       byte[] s= Decoders.BASE64.decode(secret);
       return Keys.hmacShaKeyFor(s);

    }

    public String generateToken(String  username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+15*60*1000))
                .signWith(geyKey())
                .compact();
    }

    private Claims exClaims(String username){
        return Jwts.parser()
                .verifyWith(geyKey())
                .build().
                parseSignedClaims(username)
                .getPayload();
    }

    public String getUsername(String token){
        System.err.println(exClaims(token).getSubject());
        return  exClaims(token).getSubject();
    }

    public boolean isExpired(String token){
        return exClaims(token).getExpiration().before(new Date());
    }


    public boolean getValidate(String token, UserDetails userDetails) {
      String username=  exClaims(token).getSubject();
        return username.equals(userDetails.getUsername())&&!isExpired(token);
    }
}
