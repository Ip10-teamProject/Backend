package com.example.demo.users.domain;

import com.example.demo.store.entity.Store;
import com.example.demo.store.entity.StoreMapping;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "p_user")
public class User {

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

  public User(String username, String password, String email, String nickname,UserRoleEnum role) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.nickname = nickname;
    this.role = role;
  }


}
