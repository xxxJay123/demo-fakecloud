package com.example.demofakecloud.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
/* import org.springframework.security.core.userdetails.UserDetails; */
import org.springframework.stereotype.Service;
import com.example.demofakecloud.entity.User;
import redis.clients.jedis.Jedis;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;
import java.security.Key;


@Service
public class JwtTokenService {

  @Value("${jwt.secret}")
  private final String secret;
  @Value("${jwt.expiration}")
  private final Long expiration;

  @Autowired
  private final Jedis jedis;


  public JwtTokenService(@Value("${jwt.secret}") String secret,
      @Value("${jwt.expiration}") Long expiration, Jedis jedis) {
    this.secret = secret;
    this.expiration = expiration;
    this.jedis = jedis;
  }

  public String generateToken(User user) {
    Key key = Keys.hmacShaKeyFor(secret.getBytes());
    String token = Jwts.builder()//
        .setSubject(user.getUsername())//
        .setExpiration(new Date(System.currentTimeMillis() + expiration))//
        .signWith(key)//
        .compact();//

    // Store the token in Redis with the username as the key
    jedis.set(user.getUsername(), token);

    return token;
  }


  public Date extractExpirationDate(String token) {
    return extractClaims(token).getExpiration();
  }


  public String extractUsername(String token) {
    return Jwts.parserBuilder()//
        .setSigningKey(secret.getBytes())//
        .build()///
        .parseClaimsJws(token)//
        .getBody()//
        .getSubject();//
  }

  public boolean isTokenExpired(String token) {
    return Jwts.parserBuilder()//
        .setSigningKey(secret.getBytes())//
        .build()//
        .parseClaimsJws(token)//
        .getBody()//
        .getExpiration()//
        .before(new Date());//
  }

  private Claims extractClaims(String token) {

    return Jwts.parserBuilder()//
        .setSigningKey(secret.getBytes())//
        .build()//
        .parseClaimsJws(token)//
        .getBody();//

  }


}
