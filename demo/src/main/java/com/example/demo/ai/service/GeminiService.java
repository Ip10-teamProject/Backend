package com.example.demo.ai.service;

import com.example.demo.ai.dto.GeminiRequest;
import com.example.demo.ai.dto.GeminiResponse;
import com.example.demo.ai.entity.GeminiInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {
  public static final String GEMINI_PRO = "gemini-pro";
  public static final String GEMINI_ULTIMATE = "gemini-ultimate";
  public static final String GEMINI_PRO_VISION = "gemini-pro-vision"; // text + image

  private final GeminiInterface geminiInterface;

  @Autowired
  public GeminiService(GeminiInterface geminiInterface) {
    this.geminiInterface = geminiInterface;
  }

  private GeminiResponse getCompletion(GeminiRequest request) {
    return geminiInterface.getCompletion(GEMINI_PRO, request);
  }

  public String getCompletion(String text) {
    GeminiRequest geminiRequest = new GeminiRequest(text);
    GeminiResponse response = getCompletion(geminiRequest);

    return response.getCandidates()
            .stream()
            .findFirst().flatMap(candidate -> candidate.getContent().getParts()
                    .stream()
                    .findFirst()
                    .map(GeminiResponse.TextPart::getText))
            .orElse(null);
  }
}