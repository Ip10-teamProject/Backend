package com.example.demo.location.repository;

import com.example.demo.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> ,LocationRepositoryCustom{
    boolean findByaddress(String locationName);
}
