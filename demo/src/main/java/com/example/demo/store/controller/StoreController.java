package com.example.demo.store.controller;

import com.example.demo.store.dto.StoreCreateRequestDto;
import com.example.demo.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor

public class StoreController {
    private final StoreService storeService;
    @PostMapping("")
    public ResponseEntity<String> addCategory(@RequestBody StoreCreateRequestDto storeCreateRequestDto) {
        storeService.addStore(storeCreateRequestDto);
        return ResponseEntity.ok()
                .body("asd");
    }
    @GetMapping("")
    public ResponseEntity<String> getStores() {
        storeService.getStores();
        return ResponseEntity.ok()
                .body("asd");
    }
    @PutMapping("/{storeId}")
    public ResponseEntity<String> updateStore(@PathVariable Long storeId) {
        storeService.updateStore(storeId);
        return ResponseEntity.ok()
                .body("asd");
    }
    @DeleteMapping("/{storeId}")
    public ResponseEntity<String> deleteStore(@PathVariable Long storeId) {
        storeService.deleteStore(storeId);
        return ResponseEntity.ok()
                .body("asd");
    }
    @GetMapping("/{storeId}")
    public ResponseEntity<String> addCategory(@PathVariable Long storeId) {
        storeService.getStore(storeId);
        return ResponseEntity.ok()
                .body("asd");
    }
}
