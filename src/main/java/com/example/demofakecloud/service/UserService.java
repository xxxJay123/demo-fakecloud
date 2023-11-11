package com.example.demofakecloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demofakecloud.customValidation.IsPasswordsMatching;
import com.example.demofakecloud.entity.User;
import com.example.demofakecloud.repository.UserRepository;
import lombok.RequiredArgsConstructor;


@Service
@IsPasswordsMatching
@RequiredArgsConstructor
public class UserService {
  @Autowired
  private final UserRepository userRepository;
  @Autowired
  private final PasswordEncoder passwordEncoder;


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
