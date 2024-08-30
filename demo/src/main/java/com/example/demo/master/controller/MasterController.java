package com.example.demo.master.controller;

import com.example.demo.master.service.MasterService;
import com.example.demo.users.dto.UpdateRoleDto;
import com.example.demo.users.dto.UpdateUserDto;
import com.example.demo.users.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/master")
public class MasterController {
  private final MasterService masterService;

  // 회원 단일 검색
  @GetMapping("/search")
  public UserInfoDto searchUser(
          @RequestParam(value = "username") String username
  ) {
    return masterService.findByUsername(username);
  }

  // 활성, 비활성 및 모든 회원 목록 조회
  @GetMapping("/search/list")
  public Page<UserInfoDto> userList(
          @RequestParam(value = "page", defaultValue = "1") int page,
          @RequestParam(value = "size", defaultValue = "3") int size,
          @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
          @RequestParam(value = "isPublic", required = false) Boolean isPublic
  ) {
    return masterService.userList(page - 1, size, sortBy, isPublic);
  }

  // 권한 변경을 신청한 CUSTOMER 모두 조회
  @GetMapping("/updateRole/list")
  public List<UpdateUserDto> updateRoleList(){
    return masterService.updateRoleList();
  }

  // 권한 변경 승인 및 거절
  @PostMapping("/updateRole/{userId}")
  public ResponseEntity<?> updateRole(
          @PathVariable("userId") Long userId,
          @RequestBody UpdateRoleDto updateRole
  ) {
    return masterService.updateRole(userId, updateRole);
  }

}
