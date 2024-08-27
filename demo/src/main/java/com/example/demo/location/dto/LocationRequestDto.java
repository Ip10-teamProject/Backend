package com.example.demo.location.dto;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
public class LocationRequestDto {
    List<String> location;
}
