package com.example.demo.users.application;

import com.example.demo.users.application.dto.UserInfoDto;
import com.example.demo.users.domain.User;
import com.example.demo.users.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  @Transactional(readOnly = false)
  public UserInfoDto updateUser(Long userId, UserInfoDto userInfoDto) {
    User user = userRepository.findById(userId).orElseThrow(()->
            new IllegalArgumentException("존재하지 않는 사용자 입니다."));

    // 회원 정보 업데이트 로직
    if (userInfoDto.getEmail() != null) {
      user.setEmail(userInfoDto.getEmail());
    }
    if (userInfoDto.getNickname() != null) {
      user.setNickname(userInfoDto.getNickname());
    }
    // 엔티티를 DTO로 변환하여 반환
    User savedUser = userRepository.save(user);
    savedUser.setUpdatedBy(savedUser.getUsername());
    return UserInfoDto.fromEntity(savedUser);
  }

}
