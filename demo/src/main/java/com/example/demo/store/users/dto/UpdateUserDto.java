package com.example.demo.store.users.dto;

import lombok.Getter;

@Getter
public class UpdateUserDto {
  private  Long id;
  private  String username;
  private  String role;

  public UpdateUserDto(Long id, String username, String role) {
    this.id = id;
    this.username = username;
    this.role = role;
  }
}
