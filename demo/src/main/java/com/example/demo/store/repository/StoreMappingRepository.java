package com.example.demo.store.repository;

import com.example.demo.category.repository.CategoryRepositoryCustom;
import com.example.demo.store.entity.StoreMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreMappingRepository extends JpaRepository<StoreMapping, UUID> {
}
