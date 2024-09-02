package com.example.demo.users.controller;

import com.example.demo.users.dto.CacheDto;
import com.example.demo.users.dto.LoginRequestDto;
import com.example.demo.users.dto.SignupRequestDto;
import com.example.demo.users.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
  public CacheDto login(
          @RequestBody
          LoginRequestDto requestDto
  ) {
    // 사용자 로그인 확인하고 토큰 생성, 토큰 반환
    return authService.login(requestDto);
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(
          @RequestBody LoginRequestDto requestDto,
          HttpServletRequest request
  ) {
    // "Bearer " 이후의 토큰 값만 추출
    String token = extractTokenFromRequest(request);
    // 사용자 로그아웃 처리
    authService.logout(requestDto, token);

    return ResponseEntity.ok("로그아웃 성공");
  }

  // 토큰이 블랙리스트인지 검증하는 API
  @GetMapping("/blacklist")
  public ResponseEntity<?> blacklist(
          HttpServletRequest request
  ) {
    String token = extractTokenFromRequest(request);

    boolean isBlacklisted = authService.isTokenBlacklisted(token);
    // 토큰이 블랙리스트에 있을경우 "403 NOT_FOUND" , 없을 경우 "200 OK" 반환
    if (isBlacklisted) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("토큰이 블랙리스트에 있습니다.");
    } else {
      return ResponseEntity.status(HttpStatus.OK).body("토큰이 블랙리스트에 없습니다.");
    }
  }

  // "Bearer " 이후의 토큰 값만 추출
  private String extractTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    throw new IllegalArgumentException("유효한 토큰이 없습니다.");
  }

}