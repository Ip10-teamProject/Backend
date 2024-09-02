package com.example.demo.users.dto;

import com.example.demo.users.domain.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Setter
@Getter
public class CacheDto implements Serializable {
  private String username;
  private String role;
  private String accessToken;

  public CacheDto(String username, String role, String token) {
    this.username = username;
    this.role = role;
    this.accessToken = token;
  }
}
