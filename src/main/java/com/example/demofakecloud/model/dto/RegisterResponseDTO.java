package com.example.demofakecloud.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class RegisterResponseDTO {
  private String message;
  private String username;

}
