package com.example.demo.ai.controller;

import com.example.demo.ai.dto.GeminiRequest;
import com.example.demo.ai.dto.GeminiResponse;
import com.example.demo.ai.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GeminiController {
  private final GeminiService geminiService;


  @PostMapping("/ai/recommend")
  public GeminiResponse getGeminiCompletion(
          @RequestBody
          GeminiRequest request
  ) {
    // 서비스에서 API 호출하고 응답 받기
    return geminiService.RequestAndResponseSave(request);
  }

}