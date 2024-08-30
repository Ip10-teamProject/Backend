package com.example.demo.location.controller;

import com.example.demo.location.dto.LocationRequestDto;
import com.example.demo.location.dto.LocationResponseDto;
import com.example.demo.location.dto.UpdateLocationRequestDto;
import com.example.demo.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;


    @PreAuthorize("hasAnyRole('MASTER')")
    @PostMapping("")
    public ResponseEntity<List<LocationResponseDto>> addLocations(@RequestBody LocationRequestDto locationRequestDto) {
        return ResponseEntity.ok()
                .body(locationService.addLocations(locationRequestDto));
    }
    @PreAuthorize("permitAll()")
    @GetMapping("")
    public ResponseEntity<List<LocationResponseDto>> getLocations() {
        return ResponseEntity.ok()
                .body(locationService.getLocations());
    }
    @PreAuthorize("hasAnyRole('MASTER')")
    @DeleteMapping("/{locationId}")
    public ResponseEntity<String> deleteLocation(@PathVariable UUID locationId) {
        locationService.deleteLocation(locationId);
        return ResponseEntity.ok()
                .body(locationId+"위치"+" "+"삭제완료");
    }
    @PreAuthorize("hasAnyRole('MASTER')")
    @PutMapping("/{locationId}")
    public ResponseEntity<LocationResponseDto> updateLocation(@PathVariable UUID locationId,@RequestBody UpdateLocationRequestDto updateLocationRequestDto) {
        return ResponseEntity.ok()
                .body(locationService.updateLocation(locationId,updateLocationRequestDto.getLocation()));
    }
}
