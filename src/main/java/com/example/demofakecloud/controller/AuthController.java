package com.example.demofakecloud.controller;

import java.util.Collections;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demofakecloud.entity.Role;
import com.example.demofakecloud.entity.User;
import com.example.demofakecloud.model.dto.AuthResponseDTO;
import com.example.demofakecloud.model.dto.LoginDTO;
import com.example.demofakecloud.model.dto.RegisterDTO;
import com.example.demofakecloud.repository.RoleRepository;
import com.example.demofakecloud.repository.UserRepository;
import com.example.demofakecloud.utils.JWTGenerator;
import org.springframework.security.core.AuthenticationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private JWTGenerator jwtGenerator;

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterDTO registerDto) {
    if (userRepository.existsByUserName(registerDto.getUserName())) {
      return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
    }

    User user = new User();
    user.setUserName(registerDto.getUserName());
    user.setUserPassword(
        passwordEncoder.encode((registerDto.getUserPassword())));
    user.setUserEmail(registerDto.getUserEmail());

    Optional<Role> optionalRole = roleRepository.findById(1L);
    if (optionalRole.isPresent()) {
      Role role = optionalRole.get();
      user.setRoles(Collections.singletonList(role));
    } else {
      return new ResponseEntity<>("Role not found!", HttpStatus.BAD_REQUEST);
    }

    userRepository.save(user);

    return new ResponseEntity<>("User registered success!", HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginDTO loginDto) {

    // Find user by username
    User user = userRepository.findByUserName(loginDto.getUserName());

    if (user == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // Check if password matches
    if (!passwordEncoder.matches(loginDto.getUserPassword(),
        user.getPassword())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // Generate JWT token
    Authentication authentication = new UsernamePasswordAuthenticationToken(
        user, null, user.getAuthorities());
    String token = jwtGenerator.generateToken(authentication);


    return ResponseEntity.ok(token);

  }

  // @PostMapping("/login")
  // public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDto) {
  // log.info("Attempting login for user: {}", loginDto.getUserName());
  // try {
  // Authentication authentication = authenticationManager.authenticate(
  // new UsernamePasswordAuthenticationToken(loginDto.getUserName(),
  // loginDto.getUserPassword()));
  // SecurityContextHolder.getContext().setAuthentication(authentication);
  // String token = jwtGenerator.generateToken(authentication);
  // log.info("Login successful for user: {}", loginDto.getUserName());
  // log.info("Token: {}", token);
  // return ResponseEntity.ok(new AuthResponseDTO(token));
  // } catch (AuthenticationException e) {
  // log.error("Authentication failed for user: {}", loginDto.getUserName(),
  // e);
  // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
  // }
  // }
}
