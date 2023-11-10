package com.example.demofakecloud.service;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demofakecloud.customValidation.IsPasswordsMatching;

import com.example.demofakecloud.entity.User;

import com.example.demofakecloud.repository.RoleRepository;
import com.example.demofakecloud.repository.UserRepository;
import com.example.demofakecloud.utils.JWTGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Service
@IsPasswordsMatching
@RequiredArgsConstructor
@Slf4j
public class UserService {
  @Autowired
  private final UserRepository userRepository;
  @Autowired
  private final PasswordEncoder passwordEncoder;
  @Autowired
  private JWTGenerator jwtTokenService;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private final Jedis jedis;
  @Autowired
  private AuthenticationManager authenticationManager;

  public boolean isValidUser(String username, String password) {
    User user = findUserByUsername(username);
    return user != null
        && passwordEncoder.matches(password, user.getUserPassword());
  }

  public User findUserByUsername(String username) {
    return userRepository.findByUserName(username);
  }

  public UserDetailsService userDetailsService() {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUserName(username);

      }
    };
  }

}
