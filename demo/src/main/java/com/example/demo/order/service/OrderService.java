package com.example.demo.order.service;

import com.example.demo.menu.dto.OrderMenuUpdateRequestDto;
import com.example.demo.menu.entity.Menu;
import com.example.demo.menu.repository.MenuRepository;
import com.example.demo.order.dto.OrderReqDto;
import com.example.demo.order.dto.OrderResDto;
import com.example.demo.order.dto.OrderStatusReqDto;
import com.example.demo.order.dto.OrderUpdateReqDto;
import com.example.demo.order.entity.Order;
import com.example.demo.order.entity.OrderMenu;
import com.example.demo.order.entity.OrderType;
import com.example.demo.order.repository.OrderMenuRepository;
import com.example.demo.order.repository.OrderRepository;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.store.entity.Store;
import com.example.demo.store.repository.StoreRepository;
import com.example.demo.users.domain.User;
import com.example.demo.users.domain.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    //한번의 주문에 동일 메뉴를 여러개 주문할 수 있도록 수정해야됨

    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public String create(OrderReqDto orderReqDto, CustomUserDetails userDetails) {
        Store store = existStore(orderReqDto.getStoreId());
        List<UUID> menuIdList = new ArrayList<>();

        orderReqDto.getOrderMenuCreateRequestDtos().stream()
                .forEach(orderMenuCreateRequestDto -> {
                    menuIdList.add(orderMenuCreateRequestDto.getMenuId());
                });
        List<Menu> menus = getMenusInStore(store, menuIdList);
        Order order = orderRepository.save(new Order(orderReqDto, userDetails.getUser(), store, menus, OrderType.ONLINE));

        for (Menu menu : menus) {
            if (menu.getOutOfStock()) {
                throw new IllegalArgumentException("품절된 메뉴가 있습니다.");
            }
            orderMenuRepository.save(new OrderMenu(order, menu));
            menu.minusStock();
        }

        return "생성완료";
    }

    @Transactional
    public String offLine(OrderReqDto orderReqDto, CustomUserDetails userDetails) {
        Store store = existStore(orderReqDto.getStoreId());

        if (store.getUser() != userDetails.getUser()) {
            throw new IllegalArgumentException("자신의 가게가 아닙니다.");
        }

        List<UUID> menuIdList = new ArrayList<>();

        orderReqDto.getOrderMenuCreateRequestDtos().stream()
                .forEach(orderMenuCreateRequestDto -> {
                    menuIdList.add(orderMenuCreateRequestDto.getMenuId());
                });

        List<Menu> menus = getMenusInStore(store, menuIdList);
        Order order = orderRepository.save(new Order(orderReqDto, userDetails.getUser(), store, menus, OrderType.OFFLINE));

        for (Menu menu : menus) {
            if (menu.getOutOfStock()) {
                throw new IllegalArgumentException("품절된 메뉴가 있습니다.");
            }
            orderMenuRepository.save(new OrderMenu(order, menu));
            menu.minusStock();
        }

        return "생성완료";
    }

    public List<OrderResDto> getAll() {
        List<Order> orderList = orderRepository.findAll();
        List<OrderResDto> orderResDtoList = new ArrayList<>();

        for (Order order : orderList) {
            orderResDtoList.add((new OrderResDto(order)));
        }

        return orderResDtoList;
    }

    public List<OrderResDto> getListByUserId(CustomUserDetails userDetails) {
        User user = userDetails.getUser();

        List<Order> orderList = orderRepository.findAllByUserId(user.getId());
        List<OrderResDto> orderResDtoList = new ArrayList<>();

        for (Order order : orderList) {
            orderResDtoList.add((new OrderResDto(order)));
        }

        return orderResDtoList;
    }

    @Transactional
    public List<OrderResDto> getListByMyStore(UUID storeId, CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Store store = existStore(storeId);

        if (store.getUser() != user) {
            throw new IllegalArgumentException("내 가게가 아닙니다.");
        }

        List<Order> orderList = orderRepository.findAllByStore(store);
        List<OrderResDto> orderResDtoList = new ArrayList<>();

        for (Order order : orderList) {
            orderResDtoList.add((new OrderResDto(order)));
        }

        return orderResDtoList;
    }

    @Transactional
    public OrderResDto update(UUID orderId, OrderUpdateReqDto orderUpdateReqDto, CustomUserDetails userDetails) { //미완성
        Order order = existOrder(orderId);
        Store store = existStore(orderUpdateReqDto.getStoreId());

        // 기존 주문 메뉴들을 Map으로 변환 (menuId를 key로 사용)
        Map<UUID, OrderMenu> existingOrderMenus = order.getOrderMenus().stream()
                .collect(Collectors.toMap(orderMenu -> orderMenu.getMenu().getId(), orderMenu -> orderMenu));

        // 새로운 주문 메뉴 요청을 처리
        List<OrderMenu> updatedOrderMenus = new ArrayList<>();

        for (OrderMenuUpdateRequestDto orderMenuUpdateRequestDto : orderUpdateReqDto.getOrderMenuUpdateRequestDtos()) {
            UUID menuId = orderMenuUpdateRequestDto.getMenuId();
            Integer amount = orderMenuUpdateRequestDto.getAmount();

            if (existingOrderMenus.containsKey(menuId)) {
                // 기존 메뉴가 있는 경우 수량 업데이트
                OrderMenu existingOrderMenu = existingOrderMenus.get(menuId);
                int difference = existingOrderMenu.getAmount() - amount;
                Menu menu = menuRepository.findById(existingOrderMenu.getMenu().getId()).get();
                int stock = menu.getStock() + difference;
                if (stock < 0) {
                    throw new IllegalArgumentException("재고는 0 미만일 수 없습니다.");
                } else if (stock == 0) {
                    menu.setStock(0);
                    menu.setOutOfStock(true);
                } else {
                    menu.setStock(stock);
                    menu.setOutOfStock(false);
                }
                menu.setStock(menu.getStock() + difference);
                existingOrderMenu.setAmount(amount);
                updatedOrderMenus.add(existingOrderMenu);
                existingOrderMenus.remove(menuId); // 이후 처리된 메뉴는 제거
            } else {
                // 새로운 메뉴인 경우 추가
                Menu menu = menuRepository.findById(orderMenuUpdateRequestDto.getMenuId()).get(); // 새로 추가할 메뉴 불러오기
                OrderMenu newOrderMenu = new OrderMenu(order, menu, amount);
                updatedOrderMenus.add(newOrderMenu);
            }
        }

        // 기존 주문에 있었지만 새 요청에 없는 메뉴들은 삭제 처리
        existingOrderMenus.values().forEach(orderMenu -> {delete(orderMenu.getOrder().getId(), userDetails);});

        // 업데이트된 OrderMenu 리스트를 주문에 반영
        order.setOrderMenus(updatedOrderMenus);

        // 주문 상태 업데이트 등 추가적인 로직을 여기에 구현
        // ...

        // 최종적으로 수정된 주문 정보를 반환
        return new OrderResDto(order);
    }

    @Transactional
    public String cancel(UUID orderId, CustomUserDetails userDetails) {
        Order order = existOrder(orderId);
        checkMine(userDetails.getUser(), order);

        order.cancel();

        return "주문이 취소되었습니다.";
    }

    @Transactional
    public String delete(UUID orderId, CustomUserDetails userDetails) {
        Order order = existOrder(orderId);
        User user = userDetails.getUser();

        if (user.getRole() != UserRoleEnum.MASTER) {
            checkMine(user, order);
        }

        orderRepository.delete(order);
        orderMenuRepository.deleteByOrderId(orderId);

        return "주문 삭제가 완료되었습니다.";
    }

    @Transactional
    public Page<OrderResDto> search(int page, int size, String sortBy, boolean isAsc) { //가게로 검색하도록 설정하면 될듯
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Order> ordersPage = orderRepository.findAll(pageable);

        return ordersPage.map(OrderResDto::new);
    }

    public OrderResDto getOne(UUID orderId, CustomUserDetails userDetails) {
        Order order = existOrder(orderId);
        User user = userDetails.getUser();

        if (user.getRole() == UserRoleEnum.CUSTOMER) {
            checkMine(user, order);
        }

        return new OrderResDto(order);
    }

    public String changeStatus(UUID orderId, CustomUserDetails userDetails, OrderStatusReqDto orderStatusReqDto) {
        Order order = existOrder(orderId);
        User user = userDetails.getUser();

        if (user.getRole() == UserRoleEnum.OWNER) {
            checkMyStore(user, order);
        }

        order.updateStatus(orderStatusReqDto.getStatus());

        return "주문상태 변경 성공";
    }

    private void checkMine(User user, Order order) {
        if (!order.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("내 주문이 아닙니다.");
        }
    }

    private Order existOrder(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(
            () -> new IllegalArgumentException("주문이 존재하지 않습니다.")
        );
    }

    private Store existStore(UUID storeId) {
        return storeRepository.findById(storeId).orElseThrow(
            () -> new IllegalArgumentException("가게가 존재하지 않습니다.")
        );
    }

    private void checkMyStore(User user, Order order) {
        if (order.getStore().getUser() != user) {
            throw new IllegalArgumentException("내 가게가 아닙니다.");
        }
    }

    private List<Menu> getMenusInStore(Store store, List<UUID> menuIdList) {
        List<Menu> menus = menuRepository.findByIdInAndStore(menuIdList, store);

        if (menus.size() != menuIdList.size()) {
            throw new IllegalArgumentException("해당 가게에 존재하지 않는 메뉴가 있습니다.");
        }

        return menus;
    }

    private List<Menu> getMenusInStoreForUpdate(Store store, List<OrderMenuUpdateRequestDto> orderMenuUpdateRequestDtos) {
        List<UUID> menuIdList = new ArrayList<>();
        for (OrderMenuUpdateRequestDto orderMenuUpdateRequestDto: orderMenuUpdateRequestDtos) {
            menuIdList.add(orderMenuUpdateRequestDto.getMenuId());
        }

        List<Menu> menus = menuRepository.findByIdInAndStore(menuIdList, store);

        if (menus.size() != menuIdList.size()) {
            throw new IllegalArgumentException("해당 가게에 존재하지 않는 메뉴가 있습니다.");
        }

        return menus;
    }
}
