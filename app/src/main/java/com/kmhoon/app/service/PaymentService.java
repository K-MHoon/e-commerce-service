package com.kmhoon.app.service;

import com.kmhoon.app.entity.AuthorizationEntity;
import com.kmhoon.app.entity.OrderEntity;
import com.kmhoon.app.model.PaymentReq;
import com.kmhoon.app.repository.OrderRepository;
import com.kmhoon.app.repository.PaymentRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public Mono<AuthorizationEntity> authorize(@Valid Mono<PaymentReq> paymentReq) {
        return Mono.empty();
    }

    public Mono<AuthorizationEntity> getOrdersPaymentAuthorization(@NotNull String orderId) {
        return orderRepository.findById(UUID.fromString(orderId)).map(OrderEntity::getAuthorizationEntity);
    }
}
