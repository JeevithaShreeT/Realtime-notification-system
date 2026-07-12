package com.example.user_service.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class Jwtutil {

     @Value("${jwt.secret}")
      private String key;

     @Value("${jwt.expiration}")
      private long expiration;

     public Key getSignedKey(){
         return Keys.hmacShaKeyFor(key.getBytes());
     }

     public String generateToken(String email){

        return Jwts.builder().setSubject(email)
                 .setIssuedAt(new Date())
                 .setExpiration(new Date(System.currentTimeMillis() + expiration))
                 .signWith(getSignedKey())
                 .compact();

     }

     public String extractEmail(String token){

         String email = Jwts.parserBuilder()
                 .setSigningKey(getSignedKey())
                 .build()
                 .parseClaimsJws(token)
                 .getBody()
                 .getSubject();

         return email;

     }

     public boolean validateToken(String token){
         try{
             Jwts.parserBuilder()
                     .setSigningKey(getSignedKey())
                     .build()
                     .parseClaimsJws(token);

             return true;
         }
         catch (JwtException e){

             System.out.println(e.getMessage());
             return false;

         }
     }
}
