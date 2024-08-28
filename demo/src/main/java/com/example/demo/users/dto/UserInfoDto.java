package com.example.demo.users.dto;

import com.example.demo.users.domain.User;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfoDto {
  private String username;
  private String email;
  private String nickname;
  private String role;
  private boolean isPublic;

  public static UserInfoDto fromEntity(User user) {
    return UserInfoDto.builder()
            .username(user.getUsername())
            .email(user.getEmail())
            .nickname(user.getNickname())
            .role(String.valueOf(user.getRole()))
            .isPublic(user.isPublic())
            .build();
  }

}