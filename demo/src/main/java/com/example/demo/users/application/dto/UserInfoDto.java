package com.example.demo.users.application.dto;

import com.example.demo.users.domain.User;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
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