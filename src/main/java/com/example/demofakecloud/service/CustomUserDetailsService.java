/* package com.example.demofakecloud.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.demofakecloud.entity.Role;
import com.example.demofakecloud.entity.User;
import com.example.demofakecloud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userRepository.findByUserName(username);
      if (user == null) {
          throw new UsernameNotFoundException("Username not found: " + username);
      }
      // Create UserDetails object from your User entity
      return org.springframework.security.core.userdetails.User
              .withUsername(username)//
              .password(user.getPassword()) // Assuming getPassword() returns the hashed password
              .authorities("ROLE_USER") // Add user roles here if applicable
              .accountExpired(false)//
              .accountLocked(false)//
              .credentialsExpired(false)//
              .disabled(false)//
              .build();//
  }

  private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());
  }
}
 */