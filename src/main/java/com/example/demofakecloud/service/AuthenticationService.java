package com.example.demofakecloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.demofakecloud.entity.User;
import com.example.demofakecloud.entity.Impl.CustomUserDetails;
import com.example.demofakecloud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
  @Autowired
  private final JwtTokenService jwtTokenService;
  @Autowired
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    User user = userRepository.findByUserName(username);
    Authentication authentication = new UsernamePasswordAuthenticationToken(
        user, null, user.getAuthorities());
    String token = jwtTokenService.generateToken(authentication);
    return new CustomUserDetails(user, token);

  }
}
