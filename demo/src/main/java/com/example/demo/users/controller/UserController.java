package com.example.demo.users.controller;

import com.example.demo.users.service.UserService;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.users.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  // 로그 아웃
  @PostMapping("/logout")
  public void logout(
  ) {
    // 일단 보류. 사용자의 토큰을 Redis에 저장해서 Redis에서 반환하기
  }

  // 로그인한 회원 내 정보 조회
  @GetMapping() // 로그인한 내 정보 조회
  public UserInfoDto getUser(
          @AuthenticationPrincipal // 회원 정보 받아오기
          CustomUserDetails userDetails
  ) {

    return userService.getUser(userDetails);
  }

  // 로그인한 회원 내 정보 수정
  @PatchMapping()
  public UserInfoDto updateUser(
          @RequestBody
          UserInfoDto userInfoDto,
          @AuthenticationPrincipal // 회원 정보 받아오기
          CustomUserDetails userDetails
  ) {
    return userService.updateUser(userInfoDto, userDetails);
  }

  // 권한 변경 신청 CUSTOMER -> OWNER
  @PostMapping("/updateRole")
  public ResponseEntity<?> updateUserRole(
          @AuthenticationPrincipal // 회원 정보 받아오기
          CustomUserDetails userDetails
  ) {
     userService.updateUserRole(userDetails);
     return ResponseEntity.ok("사용자 권한 변경 신청 대기");
  }

}
