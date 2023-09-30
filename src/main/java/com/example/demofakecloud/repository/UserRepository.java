package com.example.demofakecloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demofakecloud.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByUserName(String username);

  User findByUserEmail(String email);

  User findByAuthToken(String authToken);



}
