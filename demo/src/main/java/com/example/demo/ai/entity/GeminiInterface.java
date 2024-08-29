package com.example.demo.ai.entity;

import com.example.demo.ai.dto.GeminiRequest;
import com.example.demo.ai.dto.GeminiResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/v1beta/models/")
public interface GeminiInterface {
  @PostExchange("gemini-1.5-flash-latest:generateContent")
  GeminiResponse getCompletion(
          @PathVariable String model,
          @RequestBody GeminiRequest request
  );
}
