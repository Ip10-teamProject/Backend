package com.example.demo.users.service;

import com.example.demo.security.JwtRequestFilter;
import com.example.demo.users.domain.User;
import com.example.demo.users.domain.UserRepository;
import com.example.demo.users.domain.UserRoleEnum;
import com.example.demo.users.dto.CacheDto;
import com.example.demo.users.dto.LoginRequestDto;
import com.example.demo.users.dto.SignupRequestDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtRequestFilter jwtRequestFilter;

  private final RedisTemplate<String, String> redisTemplate;
  private static final String TOKEN_BLACKLIST_PREFIX = "blacklist:";

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

    // 사용자 등록
    User user = new User(username, password, email, nickname);
    user.setCreatedBy(username);
    user.setUpdatedBy(username);
    user.setRole(UserRoleEnum.CUSTOMER);
    userRepository.save(user);
  }

  // 사용자 로그인 확인 및 토큰 생성
  @CachePut(cacheNames = "userCache", key = "#requestDto.username")
  public CacheDto login(LoginRequestDto requestDto) {
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

    // 확인된 사용자 토큰 생성
    String token = createToken(checkUser);

    // 로그인한 정보를 바탕으로 Redis에 캐싱
    return new CacheDto(
            checkUser.getUsername(),
            String.valueOf(checkUser.getRole()),
            token
    );
  }

  // 로그아웃, 토큰 blacklist 등록
  public void logout(LoginRequestDto requestDto, String token) {
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

    // 토큰 검증 -> false 반환시 유효하지 않은 토큰
    if(!jwtRequestFilter.validateToken(token, checkUser.getId())){
      throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
    }
    AddBlacklist(token);
  }

  // 블랙리스트에 토큰 추가
  public void AddBlacklist(String token){
    redisTemplate.opsForValue().set(TOKEN_BLACKLIST_PREFIX + token, "true");
    // 1시간 동안 블랙 리스트에서 해당 토큰을 관리
    redisTemplate.expire(TOKEN_BLACKLIST_PREFIX + token, 1, TimeUnit.HOURS);
  }

  // 토큰 블랙리스트 검증
  public boolean isTokenBlacklisted(String token) {
    return jwtRequestFilter.isTokenBlacklisted(token);
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