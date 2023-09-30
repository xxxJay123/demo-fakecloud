package com.example.demofakecloud.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demofakecloud.customValidation.IsPasswordsMatching;
import com.example.demofakecloud.entity.User;
import com.example.demofakecloud.repository.UserRepository;
import redis.clients.jedis.Jedis;

@Service
@IsPasswordsMatching
public class UserService {
  @Autowired
  private final UserRepository userRepository;
  @Autowired
  private final PasswordEncoder passwordEncoder;
  @Autowired
  private JwtTokenService jwtTokenService;
  @Autowired
  private final Jedis jedis;

  public UserService(UserRepository userRepository,
      PasswordEncoder passwordEncoder, Jedis jedis) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jedis = jedis;
  }

  public User registerUser(User user) {
    // Generate a unique token for the user
    String token = jwtTokenService.generateToken(user);
    user.setAuthToken(token);
    // Store the user in the database
    userRepository.save(user);
    // Store the token in Redis with the username as the key
    jedis.set(user.getUsername(), token);
    // Store the user in the database
    return user;
  }



  public User findUserByUsername(String username) {
    return userRepository.findByUserName(username);
  }

  public User findUserByAuthToken(String authToken) {
    return userRepository.findByAuthToken(authToken);
  }

  public boolean isValidUser(String username, String password) {
    User user = findUserByUsername(username);
    return user != null
        && passwordEncoder.matches(password, user.getUserPassword());
  }


}
