package com.example.demo.users.controller;

import com.example.demo.security.CustomUserDetails;
import com.example.demo.users.application.UserService;
import com.example.demo.users.application.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  // 활성, 비활성 포함 모든 회원 목록 조회 (관리자 기능)
  @PreAuthorize("hasRole('MASTER')")
  @GetMapping()
  public Page<UserInfoDto> userList(
          @RequestParam(value = "page", defaultValue = "1") int page,
          @RequestParam(value = "size", defaultValue = "3") int size,
          @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
          @RequestParam(value = "isPublic", required = false) Boolean isPublic
  ) {
    return userService.userList(page - 1, size, sortBy, isPublic);
  }

  // 회원 단일 검색 (관리자 기능)
  @PreAuthorize("hasRole('MASTER')")
  @GetMapping("/search")
  public UserInfoDto searchUser(
          @RequestParam(value = "username") String username
  ){
    return userService.findByUsername(username);
  }

  // 로그 아웃
  @PostMapping("/logout")
  public void logout(
  ) {
    // 일단 보류. 사용자의 토큰을 Redis에 저장해서 Redis에서 반환하기
  }

  // 회원 정보 조회
  @GetMapping("/{userId}")
  public UserInfoDto getUser(
          @PathVariable("userId")
          Long userId,
          @AuthenticationPrincipal // 회원 정보 받아오기
          CustomUserDetails userDetails
  ) {
    return userService.getUser(userId, userDetails);
  }

  // 회원 정보 수정
  @PatchMapping("/{userId}")
  public UserInfoDto updateUser(
          @PathVariable("userId")
          Long userId,
          @RequestBody
          UserInfoDto userInfoDto,
          @AuthenticationPrincipal // 회원 정보 받아오기
          CustomUserDetails userDetails
  ) {
    return userService.updateUser(userId, userInfoDto, userDetails);
  }

}
