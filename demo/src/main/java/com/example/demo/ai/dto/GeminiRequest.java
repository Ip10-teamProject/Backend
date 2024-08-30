package com.example.demo.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class GeminiRequest {

  private List<Content> contents;

  public GeminiRequest(String text) {
    TextPart part = new TextPart(text);
    Content content = new Content(Collections.singletonList(part));
    this.contents = Arrays.asList(content);
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  private static class Content {
    private List<TextPart> parts;
  }


  interface Part {}

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  private static class TextPart implements Part {
    public String text;
  }

  @Getter
  @AllArgsConstructor
  private static class InlineDataPart implements Part {
    public InlineData inlineData;
  }

  @Getter
  @AllArgsConstructor
  public static class InlineData {
    private String mimeType;
    private String data;
  }
}