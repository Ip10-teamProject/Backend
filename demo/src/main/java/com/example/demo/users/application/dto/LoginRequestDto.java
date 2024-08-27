package com.example.demo.users.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {
  @NotBlank
  @Size(min = 4, max = 10)
  @Pattern(regexp = "^[a-zA-Z0-9]{4,10}$")
  private String username;
  @NotBlank
  @Size(min = 8, max = 15)
  @Pattern(regexp = "^[a-zA-Z0-9_#$%^!-]{8,15}$")
  private String password;
}