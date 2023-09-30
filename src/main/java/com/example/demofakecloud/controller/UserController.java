package com.example.demofakecloud.controller;

import com.example.demofakecloud.entity.User;
import com.example.demofakecloud.exception.UnauthorizedException;
import com.example.demofakecloud.model.UserRegModel;
import com.example.demofakecloud.repository.UserRepository;
import com.example.demofakecloud.service.JwtTokenService;
import com.example.demofakecloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8097")

public class UserController {
  @Autowired
  private final UserService userService;

  @Autowired
  private final UserRepository userRepository;
  @Autowired
  private final JwtTokenService jwtTokenService;



  public UserController(UserService userService,
      JwtTokenService jwtTokenService, UserRepository userRepository) {
    this.userService = userService;
    this.jwtTokenService = jwtTokenService;
    this.userRepository = userRepository;
  }

  @PostMapping("/htmlregister")
  public String registerUserHtml(@RequestBody UserRegModel userRegModel) {
    User user = new User();
    user.setUserName(userRegModel.getUserName());
    user.setUserPassword(userRegModel.getUserPassword());
    user.setUserEmail(userRegModel.getUserEmail());
    // Set other necessary fields for the User entity as needed
    userService.registerUser(user);

    return "user/register";
  }

  @PostMapping("/register")
  public ResponseEntity<User> registerUser(
      @RequestBody UserRegModel userRegModel) {
    User user = new User();
    user.setUserName(userRegModel.getUserName());
    user.setUserPassword(userRegModel.getUserPassword());
    user.setUserEmail(userRegModel.getUserEmail());
    // Set other necessary fields for the User entity as needed
    User savedUser = userRepository.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
  }

  @PostMapping("/login")
  public String loginUser(@RequestBody User user) {
    if (userService.isValidUser(user.getUsername(), user.getUserPassword())) {
      // Generate and return a JWT token
      return userService.registerUser(user).toString();
    } else {
      // Handle invalid credentials
      throw new UnauthorizedException("Invalid credentials");
    }
  }

  @GetMapping("/protected")
  public String protectedResource(
      @RequestHeader("Authorization") String token) {
    // Extract username from token and perform operations on protected resource
    String username = jwtTokenService.extractUsername(token);
    // Implement logic to access the protected resource for the given user
    return "Accessed protected resource for user: " + username;
  }


}
