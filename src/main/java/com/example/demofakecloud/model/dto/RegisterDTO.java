package com.example.demofakecloud.model.dto;

import lombok.Data;

@Data
public class RegisterDTO {
  private String userName;
  private String userPassword;
  private String userEmail;
}
