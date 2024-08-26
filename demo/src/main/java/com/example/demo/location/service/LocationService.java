package com.example.demo.location.service;

import com.example.demo.location.dto.LocationRequestDto;
import com.example.demo.location.dto.LocationResponseDto;
import com.example.demo.location.entity.Location;
import com.example.demo.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    public void addLocations(LocationRequestDto locationRequestDto) {
        List<Location> locations = new ArrayList<>();
        for (String locationName :locationRequestDto.getLocation()) {
            if(!locationRepository.findByaddress(locationName)){
                locations.add(Location.createLocation(locationName));
            }
        }
        locationRepository.saveAll(locations);
    }

    public List<LocationResponseDto> getLocations() {
        return locationRepository.getLocations();
    }

    public void deleteLocation(Long locationId) {
        Optional<Location> location =locationRepository.findById(locationId);
        location.ifPresent(locationRepository::delete);
    }

    public void updateLocation(Long locationId, String locationName) {
        Optional<Location>location =locationRepository.findById(locationId);
        if(location.isPresent()){
            Location updateLocation=location.get();
            updateLocation.updateLocation(locationName);
            locationRepository.save(updateLocation);
        }else {
            //
        }
    }
}
