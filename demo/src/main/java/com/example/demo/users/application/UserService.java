package com.example.demo.users.application;

import com.example.demo.users.domain.User;
import com.example.demo.users.domain.UserRepository;
import com.example.demo.users.domain.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  //회원 단일 조회
  public UserInfoDto getUser(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(()->
            new IllegalArgumentException("존재하지 않는 사용자 입니다."));

    return UserInfoDto.fromEntity(user);
  }

  // 회원 정보 수정
  public UserInfoDto updateUser(Long userId, UserInfoDto userInfoDto) {
    User user = userRepository.findById(userId).orElseThrow(()->
            new IllegalArgumentException("존재하지 않는 사용자 입니다."));

    // 회원 정보 업데이트 로직
    if (userInfoDto.getUsername() != null) {
      user.setUsername(userInfoDto.getUsername());
    }
    if (userInfoDto.getEmail() != null) {
      user.setEmail(userInfoDto.getEmail());
    }
    if (userInfoDto.getNickname() != null) {
      user.setNickname(userInfoDto.getNickname());
    }
    // 엔티티를 DTO로 변환하여 반환
    return UserInfoDto.fromEntity(userRepository.save(user));
  }

}
