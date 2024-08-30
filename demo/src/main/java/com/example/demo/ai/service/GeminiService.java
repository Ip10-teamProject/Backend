package com.example.demo.ai.service;

import com.example.demo.ai.dto.GeminiRequest;
import com.example.demo.ai.dto.GeminiResponse;
import com.example.demo.ai.entity.Gemini;
import com.example.demo.ai.entity.GeminiInterface;
import com.example.demo.ai.entity.GeminiRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class GeminiService {
  public static final String GEMINI_PRO = "gemini-pro";
  public static final String GEMINI_ULTIMATE = "gemini-ultimate";
  public static final String GEMINI_PRO_VISION = "gemini-pro-vision"; // text + image

  private final GeminiInterface geminiInterface;
  private final GeminiRepository geminiRepository;
  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;

  // Return the result from geminiInterface.getCompletion
  public GeminiResponse getCompletion(GeminiRequest request) {
    return geminiInterface.getCompletion(GEMINI_PRO, request); // Added return statement
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

  public GeminiResponse RequestAndResponseSave(GeminiRequest request) {
    String uri = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON); // Request Body를 사용
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.set("x-goog-api-key", "AIzaSyCKxUrfs_tOFGTzQiac9YlGYqLdmopZAwE");

    HttpEntity<GeminiRequest> entity = new HttpEntity<>(request, headers);
    ResponseEntity<GeminiResponse> response = restTemplate.exchange(uri, HttpMethod.POST, entity, GeminiResponse.class);

//    Gemini gemini = new Gemini(request.getContents(), response.getBody())


    return response.getBody();
  }

}