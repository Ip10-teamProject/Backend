package com.example.demo.ai.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Gemini {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "request_contents")
  private String requestContents;
  @Column(name = "response_contents")
  private String responseContents;


  public Gemini(String request, String response) {
    this.requestContents = request;
    this.responseContents = response;
  }
}
