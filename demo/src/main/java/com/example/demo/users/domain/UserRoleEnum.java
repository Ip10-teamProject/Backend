package com.example.demo.users.domain;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
  CUSTOMER(Authority.CUSTOMER),  // 사용자 권한
  MASTER(Authority.MASTER),  // 관리자 권한
  Owner(Authority.OWNER); // 가게 주인 권한

  private final String authority;

  UserRoleEnum(String authority) {
    this.authority = authority;
  }

  public String getAuthority() {
    return this.authority;
  }

  public static class Authority {
    public static final String CUSTOMER = "ROLE_CUSTOMER";
    public static final String MASTER = "ROLE_MASTER";
    public static final String OWNER = "ROLE_OWNER";
  }

}