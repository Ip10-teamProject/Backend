package com.example.demo.master.service;

import com.example.demo.users.dto.UpdateRoleDto;
import com.example.demo.users.dto.UpdateUserDto;
import com.example.demo.users.dto.UserInfoDto;
import com.example.demo.users.domain.User;
import com.example.demo.users.domain.UserRepository;
import com.example.demo.users.domain.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MasterService {
  private final UserRepository userRepository;

  // 회원 단일 검색
  public UserInfoDto findByUsername(String username) {
    User user = userRepository.findByUsername(username).orElseThrow(() ->
            new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    return UserInfoDto.fromEntity(user);
  }

  // 활성, 비활성 및 모든 회원 목록 조회
  public Page<UserInfoDto> userList(int page, int size, String sortBy, Boolean isPublic) {
    // 최근 날짜 순으로 내림차순 정렬
    Sort sort = Sort.by(sortBy).descending();
    // 페이징 정보 설정
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<User> usersPage;
    // isPublic이 null일 경우 모든 회원 목록 조회
    if (isPublic == null) {
      usersPage = userRepository.findAll(pageable);
    } else {
      // isPublic의 값에 해당하는 모든 회원 목록 조회
      usersPage = userRepository.findAllByIsPublic(pageable, isPublic);
    }
    return usersPage.map(UserInfoDto::fromEntity);
  }

  // 권한 변경을 신청한 CUSTOMER 모두 조회
  public List<UpdateUserDto> updateRoleList() {
    // updateRole이 true이고 role이 CUSTOMER인 유저를 찾음
    List<User> users = userRepository.findAllByUpdateRoleIsTrueAndRole(UserRoleEnum.CUSTOMER);
    // 빈 리스트일 경우 빈 응답을 반환
    if (users.isEmpty()) {
      return Collections.emptyList();
    }
    // 유저 리스트를 UpdateUserDto로 변환하여 반환
    return users.stream()
            .map(user -> new UpdateUserDto(user.getId(), user.getUsername(), String.valueOf(user.getRole())))
            .collect(Collectors.toList());
  }

  // 권한 변경 승인 및 거절
  public ResponseEntity<?> updateRole(Long userId, UpdateRoleDto updateRole) {
    User user = userRepository.findById(userId).orElseThrow(() ->
            new IllegalArgumentException("존재하지 않는 사용자 입니다."));

    user.setUpdateRole(false); // 승인 및 거절 당한 CUSTOMER의 updateRole은 다시 false로 돌려준다.
    if (!updateRole.isUpdateRole()) {
      userRepository.save(user); // 업데이트된 정보를 저장
      return ResponseEntity.ok("사용자 권한 변경 신청 거절");
    } else {
      user.setRole(UserRoleEnum.OWNER);
      userRepository.save(user); // 업데이트된 정보를 저장
      return ResponseEntity.ok("사용자 권한 변경 신청 승인");
    }
  }

}
