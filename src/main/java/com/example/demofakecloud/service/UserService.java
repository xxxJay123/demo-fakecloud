package com.example.demofakecloud.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demofakecloud.customValidation.IsPasswordsMatching;
import com.example.demofakecloud.entity.User;
import com.example.demofakecloud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import redis.clients.jedis.Jedis;

@Service
@IsPasswordsMatching
@RequiredArgsConstructor
public class UserService {
  @Autowired
  private final UserRepository userRepository;
  @Autowired
  private final PasswordEncoder passwordEncoder;
  @Autowired
  private JwtTokenService jwtTokenService;
  @Autowired
  private final Jedis jedis;


/* 
  public User registerUser(User user) {
    // Hash the user password before saving it to the database
    user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));

    // Generate a unique token for the user
    String token = jwtTokenService.generateToken(user);
  
    // Store the user in the database
    userRepository.save(user);

    // Store the token in Redis with the username as the key
    jedis.set(user.getUserName(), token);

    return user;
  } */

  public boolean isValidUser(String username, String password) {
    User user = findUserByUsername(username);
    return user != null
        && passwordEncoder.matches(password, user.getUserPassword());
  }

  public User findUserByUsername(String username) {
    return userRepository.findByUserName(username);
  }
}
