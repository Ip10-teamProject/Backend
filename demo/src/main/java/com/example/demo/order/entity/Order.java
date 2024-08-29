package com.example.demo.order.entity;

import com.example.demo.global.TimeStamped;
import com.example.demo.menu.entity.Menu;
import com.example.demo.order.dto.OrderReqDto;
import com.example.demo.store.entity.Store;
import com.example.demo.users.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "p_order")
public class Order extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column
    private String memo;

    @Column
    private Integer price;

    @Column
    private String address;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @OneToMany(mappedBy = "order")
    private List<OrderMenu> orderMenus;

    public Order(OrderReqDto orderReqDto, User user, Store store, List<Menu> menus, OrderType orderType) {
        this.user = user;
        this.store = store;
        this.memo = orderReqDto.getMemo();
        this.price = menus.stream().map(Menu::getPrice).reduce(0, Integer::sum);
        this.address = orderReqDto.getAddress();
        this.type = orderType;
    }

    public Order(OrderReqDto orderReqDto, User user, Store store, List<Menu> menus, String test) {
        this.user = user;
        this.store = store;
        this.memo = orderReqDto.getMemo();
        this.price = menus.stream().map(Menu::getPrice).reduce(0, Integer::sum);
        this.address = orderReqDto.getAddress();
    }

    public void update(OrderReqDto orderReqDto) {
        checkStatusPending();

        this.memo = orderReqDto.getMemo();
    }

    public void cancel() {
        checkStatusPending();
        checkAvailableCancelTime();

        this.status = OrderStatus.CANCELED;
    }

    public void updateStatus(OrderStatus orderStatus) {
        checkAvailableChangeStatus();

        this.status = orderStatus;
    }

    private void checkStatusPending() {
        if (this.status != OrderStatus.PENDING && this.status != OrderStatus.PAID) {
            throw new IllegalArgumentException("진행중인 주문은 변경, 취소 할 수 없습니다.");
        }
    }

    private void checkAvailableChangeStatus() {
        if (this.status == OrderStatus.CANCELED || this.status == OrderStatus.COMPLETED) {
            throw new IllegalArgumentException("완료, 취소된 주문은 상태를 변경할 수 없습니다.");
        }
    }

    private void checkAvailableCancelTime() {
        if (ChronoUnit.MINUTES.between(this.getCreatedAt(), LocalDateTime.now()) > 5) {
            throw new IllegalArgumentException("주문 생성 후 5분이 지나면 취소할 수 없습니다.");
        }
    }
}
