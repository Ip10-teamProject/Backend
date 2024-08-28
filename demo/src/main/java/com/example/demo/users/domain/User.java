package com.example.demo.users.domain;

import com.example.demo.global.TimeStamped;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "p_user")
public class User extends TimeStamped implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  private String email;
  @Column(nullable = false, unique = true)
  private String nickname;

  @Enumerated(value = EnumType.STRING)
  private UserRoleEnum role;

  @Column
  private boolean isPublic = true;
  @Column // 권한 변경 신청시 -> true / 유저의 권한 신청을 관리자가 승인해주면 null, 거절하면 false
  private boolean updateRole = false;

  public User(String username, String password, String email, String nickname) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.nickname = nickname;
  }
}
