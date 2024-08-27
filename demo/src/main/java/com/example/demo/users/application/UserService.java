package com.example.demo.users.application;

import com.example.demo.security.CustomUserDetails;
import com.example.demo.users.application.dto.UserInfoDto;
import com.example.demo.users.domain.User;
import com.example.demo.users.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  //회원 단일 조회
  public UserInfoDto getUser(Long userId, CustomUserDetails userDetails) {
    // userId와, 회원 정보의 Id 비교
    checkMine(userId, userDetails);

    User user = userRepository.findById(userId).orElseThrow(()->
            new IllegalArgumentException("존재하지 않는 사용자 입니다."));

    return UserInfoDto.fromEntity(user);
  }

  // 회원 정보 수정
  @Transactional(readOnly = false)
  public UserInfoDto updateUser(Long userId, UserInfoDto userInfoDto, CustomUserDetails userDetails) {
    checkMine(userId, userDetails);
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

  // userId와, 회원 정보의 Id 비교
  private void checkMine(Long userId, CustomUserDetails userDetails){
    if(!userDetails.getId().equals(userId)){
      throw new IllegalArgumentException("토큰과 값이 일치하지 않습니다");
    }
  }

  // 활성, 비활성 포함 모든 회원 목록 조회 (관리자 기능)
  public Page<UserInfoDto> userList(int page, int size, String sortBy, Boolean isPublic) {
    // 최근 날짜 순으로 내림차순 정렬
    Sort sort = Sort.by(sortBy).descending();
    // 페이징 정보 설정
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<User> usersPage;
    // isPublic이 null일 경우 모든 회원 목록 조회
    if (isPublic == null){
      usersPage = userRepository.findAll(pageable);
    } else{
      // isPublic의 값에 해당하는 모든 회원 목록 조회
      usersPage = userRepository.findAllByIsPublic(pageable, isPublic);
    }
    return usersPage.map(UserInfoDto::fromEntity);
  }

  // 회원 단일 검색 (관리자 기능)
  public UserInfoDto findByUsername(String username) {
    User user = userRepository.findByUsername(username).orElseThrow(()->
            new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    return  UserInfoDto.fromEntity(user);
  }

}
