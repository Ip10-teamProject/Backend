package com.example.demo.payment.service;

import com.example.demo.order.entity.Order;
import com.example.demo.order.entity.OrderStatus;
import com.example.demo.order.repository.OrderRepository;
import com.example.demo.payment.dto.PaymentResDto;
import com.example.demo.payment.entity.Payment;
import com.example.demo.payment.entity.PaymentStatus;
import com.example.demo.payment.repository.PaymentRepository;
import com.example.demo.security.CustomUserDetails;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentResDto getOne(UUID paymentId, CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Payment payment = existPayment(paymentId);

        if (user.getRole() != UserRoleEnum.MASTER) {
            checkMine(payment, user);
        }

        return new PaymentResDto(payment);
    }

    public List<PaymentResDto> getList() {
        List<Payment> paymentList = paymentRepository.findAll();
        List<PaymentResDto> paymentResDtoArrayList = new ArrayList<>();

        for (Payment payment : paymentList) {
            paymentResDtoArrayList.add((new PaymentResDto(payment)));
        }

        return paymentResDtoArrayList;
    }

    @Transactional
    public String create(CustomUserDetails userDetails, UUID orderId) {
        User user = userDetails.getUser();
        Order order = existOrder(orderId);
        paymentRepository.save(new Payment(user, order));
        order.setStatus(OrderStatus.PAID);

        return "결제 성공";
    }

    private Payment existPayment(UUID paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(
            () -> new IllegalArgumentException("결제 정보가 존재하지 않습니다.")
        );
    }

    private Order existOrder(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(
            () -> new IllegalArgumentException("글로벌 예외처리 추가해야함")
        );
    }

    private void checkMine(Payment payment, User user) {
        if (payment.getUser() != user) {
            throw new IllegalArgumentException("자신의 결제내역만 조회할 수 있습니다.");
        }
    }

    @Transactional
    public Page<PaymentResDto> search(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Payment> paymentPage = paymentRepository.findAll(pageable);

        return paymentPage.map(PaymentResDto::new);
    }

    public List<PaymentResDto> getListByUserId(Long userId, CustomUserDetails userDetails) {
        User user = userDetails.getUser();

        if (user.getRole() == UserRoleEnum.CUSTOMER) {
            if (!userId.equals(user.getId())) {
                throw new IllegalArgumentException("자기 자신의 결재 내역만 확인 가능합니다.");
            }
        }

        List<Payment> paymentList = paymentRepository.findAllByUserId(userId);
        List<PaymentResDto> paymentResDtoArrayList = new ArrayList<>();

        for (Payment payment : paymentList) {
            paymentResDtoArrayList.add((new PaymentResDto(payment)));
        }

        return paymentResDtoArrayList;
    }

    @Transactional
    public String cancel(UUID paymentId, CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Payment payment = existPayment(paymentId);
        checkMine(payment, user);
        Order order = existOrder(payment.getOrder().getId());

        payment.setStatus(PaymentStatus.CANCELED);
        order.setStatus(OrderStatus.CANCELED);

        return "결제가 취소되었습니다.";
    }

    @Transactional
    public String refund(UUID paymentId) {
        Payment payment = existPayment(paymentId);

        payment.setStatus(PaymentStatus.REFUND);

        return "환불이 완료되었습니다.";
    }
}
