package com.example.demo.menu.repository;

import com.example.demo.menu.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {

    // Menu.storeId를 통해 Menu 목록을 조회
    @Query("SELECT m FROM Menu m WHERE m.store.storeId = :storeId")
    Page<Menu> findByStoreId(@Param("storeId") UUID storeId, Pageable pageable);

    Optional<Menu> findByName(String name);
}
