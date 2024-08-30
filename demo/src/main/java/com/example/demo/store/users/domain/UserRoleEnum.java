package com.example.demo.store.users.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;

@Getter
public enum UserRoleEnum implements Serializable {
  CUSTOMER(Authority.CUSTOMER),  // 사용자 권한
  MASTER(Authority.MASTER),  // 관리자 권한
  OWNER(Authority.OWNER); // 가게 주인 권한

  private final String authority;

  UserRoleEnum(String authority) {
    this.authority = authority;
  }

  public String getAuthority() {
    return this.authority;
  }

  public GrantedAuthority toGrantedAuthority() {
    return new SimpleGrantedAuthority("ROLE_" + this.name());
  }

  public static class Authority {
    public static final String CUSTOMER = "ROLE_CUSTOMER";
    public static final String MASTER = "ROLE_MASTER";
    public static final String OWNER = "ROLE_OWNER";
  }

}