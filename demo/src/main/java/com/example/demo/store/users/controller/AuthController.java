package com.example.demo.store.users.controller;

import com.example.demo.store.users.dto.LoginRequestDto;
import com.example.demo.store.users.service.AuthService;
import com.example.demo.store.users.dto.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  // 회원 가입
  @PostMapping("/signup")
  public ResponseEntity<?> signup(
          @RequestBody
          SignupRequestDto requestDto
  ) {
    authService.signup(requestDto);
    return ResponseEntity.ok("회원가입 성공");
  }

  // 로그인
  @PostMapping("/login")
  public String login(
          @RequestBody
          LoginRequestDto requestDto
  ){
    // 사용자 로그인 확인하고 토큰 생성, 토큰 반환
    return authService.login(requestDto);
  }
}