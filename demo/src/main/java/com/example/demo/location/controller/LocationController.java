package com.example.demo.location.controller;

import com.example.demo.location.dto.LocationRequestDto;
import com.example.demo.location.dto.LocationResponseDto;
import com.example.demo.location.dto.UpdateLocationRequestDto;
import com.example.demo.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @PostMapping("")
    public ResponseEntity<String> addLocations(@RequestBody LocationRequestDto locationRequestDto) {
        locationService.addLocations(locationRequestDto);
        return ResponseEntity.ok()
                .body("성공");
    }
    @GetMapping("")
    public ResponseEntity<List<LocationResponseDto>> getLocations() {
        return ResponseEntity.ok()
                .body(locationService.getLocations());
    }
    @DeleteMapping("/{locationId}")
    public ResponseEntity<String> deleteLocation(@PathVariable Long locationId) {
        locationService.deleteLocation(locationId);
        return ResponseEntity.ok()
                .body("삭제");
    }
    @PutMapping("/{locationId}")
    public ResponseEntity<String> updateLocation(@PathVariable Long locationId,@RequestBody UpdateLocationRequestDto updateLocationRequestDto) {
        locationService.updateLocation(locationId,updateLocationRequestDto.getLocation());
        return ResponseEntity.ok()
                .body("수정");
    }
}
