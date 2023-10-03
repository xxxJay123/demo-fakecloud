package com.example.demofakecloud.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenService {
  
  @Value("${jwt.expiration}")
  private Long jwtExpiration;

  private final Key key; // Your secret key, possibly injected via constructor


  public String generateToken(Authentication authentication) {
    String username = authentication.getName();
    Date currentDate = new Date();
    Date expireDate = new Date(currentDate.getTime() + jwtExpiration);

    String token = Jwts.builder().setSubject(username).setIssuedAt(new Date())
        .setExpiration(expireDate).signWith(key, SignatureAlgorithm.HS512)
        .compact();

    return token;
  }

  public String getUsernameFromJWT(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
        .parseClaimsJws(token).getBody();

    return claims.getSubject();
  }

  public boolean isTokenValid(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }
}
