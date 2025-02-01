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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public Optional<AuthorizationEntity> authorize(@Valid PaymentReq paymentReq) {
        return Optional.empty();
    }

    @Transactional(readOnly = true)
    public Optional<AuthorizationEntity> getOrdersPaymentAuthorization(@NotNull String orderId) {
        return orderRepository.findById(UUID.fromString(orderId)).map(OrderEntity::getAuthorizationEntity);
    }
}
