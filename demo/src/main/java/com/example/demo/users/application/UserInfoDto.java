package com.example.demo.users.application;

import com.example.demo.users.domain.User;
import com.example.demo.users.domain.UserRoleEnum;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoDto {
  @Size(min = 4, max = 10)
  @Pattern(regexp = "^[a-zA-Z0-9_-]{4,10}$")
  private String username;
  private String email;
  private String nickname;
  private String role;

  public static UserInfoDto fromEntity(User user){
    return UserInfoDto.builder()
            .username(user.getUsername())
            .email(user.getEmail())
            .nickname(user.getNickname())
            .role(String.valueOf(user.getRole()))
            .build();
  }

}