package com.example.demo.users.application;

import com.example.demo.users.application.dto.LoginRequestDto;
import com.example.demo.users.application.dto.SignupRequestDto;
import com.example.demo.users.domain.User;
import com.example.demo.users.domain.UserRepository;
import com.example.demo.users.domain.UserRoleEnum;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  // Token 식별자
  public static final String BEARER_PREFIX = "Bearer ";
  // 로그인한 사용자의 권한이 담길 KEY 값
  public static final String AUTHORIZATION_KEY = "role";
  // 토큰 만료시간
  private static Long TOKEN_TIME = 60 * 60 * 1000L; // 60분

  private SecretKey key;
  // JWT의 서명 algo
  private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

  @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
  private String secretKey;

  @PostConstruct
  // createToken 에서 사용할 key 초기화
  public void init() {
    byte[] bytes = Decoders.BASE64URL.decode(secretKey);
    key = Keys.hmacShaKeyFor(bytes);
  }


  // 회원 가입
  @Transactional(readOnly = false)
  public void signup(SignupRequestDto requestDto) {
    String username = requestDto.getUsername();
    String password = passwordEncoder.encode(requestDto.getPassword());

    // 회원 중복 확인
    Optional<User> checkUsername = userRepository.findByUsername(username);
    // isPresent(): Optional 클래스에서 사용되는 메서드로, Optional 객체에 값이 존재하는지를 확인
    if (checkUsername.isPresent()) {
      // 값이 존재할 경우 사용자가 이미 존재
      throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
    }

    // email 중복확인
    String email = requestDto.getEmail();
    Optional<User> checkEmail = userRepository.findByEmail(email);
    if (checkEmail.isPresent()) {
      throw new IllegalArgumentException("중복된 Email 입니다.");
    }

    // nickname 중복확인
    String nickname = requestDto.getNickname();
    Optional<User> checkNickname = userRepository.findByNickname(nickname);
    if (checkNickname.isPresent()) {
      throw new IllegalArgumentException("중복된 Nickname 입니다.");
    }

    // 사용자 ROLE 확인
    UserRoleEnum role;
    try {
      role = UserRoleEnum.valueOf(requestDto.getRole());
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException("등록할 수 없는 권한입니다.");
    }

    // 사용자 등록
    User user = new User(username, password, email, nickname, role);
    user.setCreatedBy(username);
    user.setUpdatedBy(username);
    userRepository.save(user);
  }

  // 사용자 로그인 확인 및 토큰 생성
  public String login(LoginRequestDto requestDto) {
    // 사용자 로그인 확인
    String username = requestDto.getUsername();
    String password = requestDto.getPassword();

    // 사용자 조회
    User checkUser = userRepository.findByUsername(username).orElseThrow(() ->
            new IllegalArgumentException("존재하지 않는 사용자 입니다."));

    // 비밀번호 검증
    if (!passwordEncoder.matches(password, checkUser.getPassword())) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
    }

    // 확인된 사용자 토큰 생성 및 반환
    return createToken(checkUser);

  }

  // 토큰 생성
  public String createToken(User checkUser) {
    new Date();
    new Date(System.currentTimeMillis());
    return BEARER_PREFIX +
            Jwts.builder()
                    .claim("user_id", checkUser.getId())
                    .claim("username", checkUser.getUsername())
                    .subject(checkUser.getEmail()) // 사용자 이메일
                    .claim(AUTHORIZATION_KEY, checkUser.getRole()) // 사용자 권한
                    .expiration(new Date(System.currentTimeMillis() + TOKEN_TIME)) // 만료 시간
                    .issuedAt(new Date(System.currentTimeMillis())) // 발급일
                    .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                    .compact();
  }
}