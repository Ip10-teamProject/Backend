package com.example.demo.location.repository;

import com.example.demo.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> ,LocationRepositoryCustom{
    Optional<Location> findByaddress(String locationName);
}
