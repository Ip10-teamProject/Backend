package com.example.demo.location.service;

import com.example.demo.location.dto.LocationRequestDto;
import com.example.demo.location.dto.LocationResponseDto;
import com.example.demo.location.entity.Location;
import com.example.demo.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    public List<LocationResponseDto> addLocations(LocationRequestDto locationRequestDto) {
        List<Location> locations = new ArrayList<>();
        for (String locationName :locationRequestDto.getLocation()) {
            Optional<Location> byaddress = locationRepository.findByaddress(locationName);
            if(!byaddress.isPresent()){
                locations.add(Location.createLocation(locationName));
            }
        }
        List<LocationResponseDto> locationResponseDtos = new ArrayList<>();
        for (Location location : locations) {
            locationResponseDtos.add(new LocationResponseDto(location.getLocation_id(),location.getAddress()));
        }
        locationRepository.saveAll(locations);
        return locationResponseDtos;
    }

    public List<LocationResponseDto> getLocations() {
        return locationRepository.getLocations();
    }

    public void deleteLocation(UUID locationId) {
        Optional<Location> location =locationRepository.findById(locationId);
        location.ifPresent(locationRepository::delete);
    }

    @Transactional
    public LocationResponseDto updateLocation(UUID locationId, String locationName) {
        Location location =locationRepository.findById(locationId).orElseThrow(() ->
                new NullPointerException("해당위치없음")
        );
        location.updateLocation(locationName);
        locationRepository.save(location);
        return new LocationResponseDto(location.getLocation_id(),location.getAddress());
    }
}
