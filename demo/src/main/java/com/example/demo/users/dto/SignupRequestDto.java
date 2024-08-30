package com.example.demo.users.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
  @NotBlank
  @Size(min = 4, max = 10)
  @Pattern(regexp = "^[a-zA-Z0-9]{4,10}$")
  private String username;

  @Size(min = 8, max = 15)
  @Pattern(regexp = "^[a-zA-Z0-9_#$%^!-]{8,15}$")
  @NotBlank
  private String password;

  @Email
  @NotBlank
  private String email;
  @NotBlank
  private String nickname;
}