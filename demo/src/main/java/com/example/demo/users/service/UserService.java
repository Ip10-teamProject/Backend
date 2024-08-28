package com.example.demo.users.service;

import com.example.demo.users.domain.User;
import com.example.demo.users.domain.UserRepository;
import com.example.demo.security.CustomUserDetails;

import com.example.demo.users.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  // 내정보 조회
  public UserInfoDto getUser(CustomUserDetails userDetails) {
    User user = findById(userDetails);
    return UserInfoDto.fromEntity(user);
  }

  // 회원 내 정보 수정
  @Transactional(readOnly = false)
  public UserInfoDto updateUser(UserInfoDto userInfoDto, CustomUserDetails userDetails) {
    User user = findById(userDetails);

    // 회원 내 정보 업데이트 로직
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

  // 권한 변경 신청 CUSTOMER -> OWNER
  public void updateUserRole(CustomUserDetails userDetails) {
    User user = findById(userDetails);
    user.setUpdateRole(true);
    userRepository.save(user);
  }

  // 사용자 조회
  private User findById(CustomUserDetails userDetails) {
    return userRepository.findById(userDetails.getId()).orElseThrow(() ->
            new IllegalArgumentException("존재하지 않는 사용자 입니다.")
    );
  }
}
