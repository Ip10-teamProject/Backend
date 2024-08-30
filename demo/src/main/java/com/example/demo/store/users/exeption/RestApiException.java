package com.example.demo.store.users.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestApiException {
  private String errorMessage;
  private int statusCode;
}
