package com.example.demo.store.repository;

import com.example.demo.store.entity.StoreMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreMappingRepository extends JpaRepository<StoreMapping,Long> {
}
