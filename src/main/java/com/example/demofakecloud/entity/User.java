package com.example.demofakecloud.entity;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.demofakecloud.customValidation.IsPasswordsMatching;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IsPasswordsMatching
public class User implements UserDetails/* , PasswordConfirmable */ {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String userName;
  private String userPassword;
  private String userEmail;


  private String authToken;// Token used for authentication

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // In this example, the user has a single role: "ROLE_USER"
    return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getPassword() {
    return userPassword;
  }

  @Override
  public String getUsername() {
    return userName;
  }


  // Below methods are set to true for simplicity; you can customize them based on your requirements.

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}

