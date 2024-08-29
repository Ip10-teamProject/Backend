package com.example.demo.menu.repository;

import com.example.demo.menu.entity.Menu;
import com.example.demo.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {

    // Menu.store.storeId를 통해 Menu 목록을 조회
    @Query("SELECT m FROM Menu m WHERE m.store.storeId = :storeId")
    Page<Menu> findByStoreId(@Param("storeId") UUID storeId, Pageable pageable);

    Optional<Menu> findByName(String name);

    @Query("SELECT m FROM  Menu m WHERE m.name = :name and m.store.storeId = :storeId")
    Optional<Menu> findByMenuNameAndStoreId(@Param("name") String name, @Param("storeId") UUID storeId);

    @Query("SELECT m FROM  Menu m WHERE m.id = :menuId and m.store.storeId = :storeId")
    Optional<Menu> findByMenuIdAndStoreId(@Param("menuId") UUID menuId, @Param("storeId") UUID storeId);

    List<Menu> findByIdInAndStore(List<UUID> ids, Store store);
}
