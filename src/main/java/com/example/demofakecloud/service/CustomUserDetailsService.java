package com.example.demofakecloud.service;

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
  private UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) {
    User user = userRepository.findByUserName(username);
    if (user == null) {
      throw new UsernameNotFoundException("Username not found: " + username);
    }
    return new org.springframework.security.core.userdetails.User(
        user.getUsername(), user.getUserPassword(),
        mapRolesToAuthorities(user.getRoles()));
  }

  private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());
  }
}
