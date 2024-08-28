package com.example.demo.users.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);
  Optional<User> findByNickname(String nickname);

  Page<User> findAllByIsPublic(Pageable pageable, Boolean isPublic);

  // updateRole이 true이고 role이 CUSTOMER인 유저를 찾아온다.
  List<User> findAllByUpdateRoleIsTrueAndRole(UserRoleEnum role);
}
