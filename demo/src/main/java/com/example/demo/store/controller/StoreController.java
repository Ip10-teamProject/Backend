package com.example.demo.store.controller;

import com.example.demo.menu.dto.MenuDeleteRequestDto;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.store.dto.*;
import com.example.demo.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor

public class StoreController {
    private final StoreService storeService;

    @PreAuthorize("hasAnyRole('MASTER', 'OWNER')")
    @PostMapping("")
    public ResponseEntity<StoreResponseDto> addStore(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody StoreCreateRequestDto storeCreateRequestDto) {

        return ResponseEntity.ok()
                .body(storeService.addStore(storeCreateRequestDto, userDetails.getUser()));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("")
    public ResponseEntity<Page<StoreResponseDto>> getStores(Pageable pageable) { // 가게들 조회
        return ResponseEntity.ok()
                .body(storeService.getStores(pageable));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponseDto> getStore(@PathVariable UUID storeId) { // 가게 조회
        return ResponseEntity.ok()
                .body(storeService.getStore(storeId));
    }

    @PreAuthorize("hasAnyRole('MASTER', 'OWNER')")
    @PutMapping("/{storeId}")
    public ResponseEntity<StoreResponseDto> updateStore(@PathVariable UUID storeId, @RequestBody StoreUpdateRequestDto storeUpdateRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok()
                .body(storeService.updateStore(storeId, storeUpdateRequestDto, userDetails.getUser()));
    }

    @PreAuthorize("hasAnyRole('MASTER', 'OWNER')")
    @DeleteMapping("/{storeId}")
    public ResponseEntity<String> deleteStore(@PathVariable UUID storeId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        storeService.deleteStore(storeId, userDetails.getUser());
        return ResponseEntity.ok()
                .body(storeId + "가게" + " " + "삭제완료");
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/category")
    public ResponseEntity<Page<StoreResponseDto>> categoryStores(@RequestParam("category") String name, Pageable pageable) { // 카테고리별 가게 조회
        return ResponseEntity.ok()
                .body(storeService.categoryStores(name, pageable));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/location")
    public ResponseEntity<Page<StoreResponseDto>> locationStores(@RequestParam("location") String name, Pageable pageable) { // 지역별 가게 조회
        return ResponseEntity.ok()
                .body(storeService.locationStores(name, pageable));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/")
    public ResponseEntity<Page<StoreResponseDto>> getSearchStores(@RequestParam("search") String name, Pageable pageable) { // 가게 이름으로 조회 %가게이름%
        return ResponseEntity.ok()
                .body(storeService.getSearchStores(name, pageable));
    }

    /**
     * [ 가게 별 Menu 추가 API ]
     */
    @PreAuthorize("hasAnyRole('OWNER')")
    @PostMapping("/{storeName}/menus")
    public ResponseEntity<List<StoreMenusCreateResponseDto>> createStoreMenus(@PathVariable(name = "storeName") String storeName,
                                                                              @RequestBody StoreMenusCreateRequestDto storeMenusCreateRequestDto,
                                                                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok()
                .body(storeService.createStoreMenus(storeName, storeMenusCreateRequestDto, userDetails));
    }

    /**
     * [ 가게 별 Menu 목록 조회 API ]
     * 해당 API는 Store.storeName으로 요청하지만
     * 실제 조회 로직에서는 Store.storeId로 조회합니다.
     */

    @PreAuthorize("permitAll()")
    @GetMapping("/{storeName}/menus")
    public ResponseEntity<Page<StoreMenusResponseDto>> getStoreMenus(@PathVariable(name = "storeName") String storeName,
                                                                     @AuthenticationPrincipal CustomUserDetails userDetails,
                                                                     @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok()
                .body(storeService.getMenus(storeName, userDetails, pageable));
    }


    @PreAuthorize("hasAnyRole('MASTER', 'OWNER')")
    @PatchMapping("/{storeName}/menus")
    public ResponseEntity<?> updateStoreMenus(@PathVariable(name = "storeName") String storeName,
                                              @RequestBody StoreMenusUpdateRequestDto storeMenusUpdateRequestDto,
                                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        storeService.updateStoreMenus(storeName, storeMenusUpdateRequestDto, userDetails);
        return ResponseEntity.ok("수정 완료");
    }

    @PreAuthorize("hasAnyRole('MASTER', 'OWNER')")
    @DeleteMapping("/{storeName}/menus")
    public ResponseEntity<String> deleteStoreMenus(@PathVariable(name = "storeName") String storeName,
                                                   @RequestBody MenuDeleteRequestDto storeMenusDeleteRequestDto,
                                                   @AuthenticationPrincipal CustomUserDetails userDetails) {
        storeService.deleteStoreMenus(storeName, storeMenusDeleteRequestDto, userDetails);
        return ResponseEntity.ok("삭제 완료");
    }
}
