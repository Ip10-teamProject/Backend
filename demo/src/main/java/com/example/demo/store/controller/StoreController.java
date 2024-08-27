package com.example.demo.store.controller;

import com.example.demo.security.CustomUserDetails;
import com.example.demo.store.dto.StoreCreateRequestDto;
import com.example.demo.store.dto.StoreResponseDto;
import com.example.demo.store.dto.StoreUpdateRequestDto;
import com.example.demo.store.service.StoreService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<StoreResponseDto>> getStores() {

        return ResponseEntity.ok()
                .body(storeService.getStores());
    }
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponseDto> getStore(@PathVariable UUID storeId) {
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
}
