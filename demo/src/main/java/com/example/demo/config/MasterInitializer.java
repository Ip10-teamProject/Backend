package com.example.demo.config;

import com.example.demo.store.users.domain.User;
import com.example.demo.store.users.domain.UserRepository;
import com.example.demo.store.users.domain.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j(topic = "MasterInitializer")
@Configuration
@RequiredArgsConstructor
public class MasterInitializer {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Bean
  public CommandLineRunner initializeMasterUser() {
    return args -> {
      String masterUsername = "master";

      // 이미 master 유저가 존재하는지 확인
      if (userRepository.findByUsername(masterUsername).isEmpty()) {
        // master 유저가 없으면 새로 생성
        User masterUser = User.builder()
                .username(masterUsername)
                .password(passwordEncoder.encode("master")) // 패스워드는 암호화
                .email("master@google.com")
                .nickname("master")
                .role(UserRoleEnum.MASTER)
                .isPublic(true)
                .build();

        userRepository.save(masterUser); // 사용자 저장
        log.info("master 계정 자동 생성");
      }
    };
  }

}
