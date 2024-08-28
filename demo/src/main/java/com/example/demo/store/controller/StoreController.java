package com.example.demo.store.controller;

import com.example.demo.security.CustomUserDetails;
import com.example.demo.store.dto.StoreCreateRequestDto;
import com.example.demo.store.dto.StoreResponseDto;
import com.example.demo.store.dto.StoreUpdateRequestDto;
import com.example.demo.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor

public class StoreController {
    private final StoreService storeService;
    @PostMapping("")
    public ResponseEntity<StoreResponseDto> addStore(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody StoreCreateRequestDto storeCreateRequestDto) {

        return ResponseEntity.ok()
                .body(storeService.addStore(storeCreateRequestDto ,userDetails.getId()));
    }
    @GetMapping("")
    public ResponseEntity<Page<StoreResponseDto>> getStores(Pageable pageable) { // 가게들 조회
        return ResponseEntity.ok()
                .body(storeService.getStores(pageable));
    }
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponseDto> getStore(@PathVariable UUID storeId) { // 가게 조회
        return ResponseEntity.ok()
                .body(storeService.getStore(storeId));
    }
    @PutMapping("/{storeId}")
    public ResponseEntity<StoreResponseDto> updateStore(@PathVariable UUID storeId, @RequestBody StoreUpdateRequestDto storeUpdateRequestDto) {
        storeService.updateStore(storeId,storeUpdateRequestDto);
        return ResponseEntity.ok()
                .body(storeService.updateStore(storeId,storeUpdateRequestDto));
    }
    @DeleteMapping("/{storeId}")
    public ResponseEntity<String> deleteStore(@PathVariable UUID storeId) {
        storeService.deleteStore(storeId);
        return ResponseEntity.ok()
                .body(storeId+"가게"+" "+"삭제완료");
    }

    @GetMapping("/category")
    public ResponseEntity<Page<StoreResponseDto>>  categoryStores(@RequestParam("category")String name, Pageable pageable){ // 카테고리별 가게 조회
        return ResponseEntity.ok()
                .body(storeService.categoryStores(name,pageable));
    }

    @GetMapping("/location")
    public ResponseEntity<Page<StoreResponseDto>>  locationStores(@RequestParam("location")String name, Pageable pageable){ // 지역별 가게 조회
        return ResponseEntity.ok()
                .body(storeService.locationStores(name,pageable));
    }
    @GetMapping("/")
    public ResponseEntity<Page<StoreResponseDto>>  getSearchStores(@RequestParam("search")String name, Pageable pageable){ // 가게 이름으로 조회 %가게이름%
        return ResponseEntity.ok()
                .body(storeService.getSearchStores(name,pageable));
    }
}
