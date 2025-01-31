package com.kmhoon.app.service;

import com.kmhoon.app.entity.OrderEntity;
import com.kmhoon.app.exceptions.ResourceNotFoundException;
import com.kmhoon.app.model.NewOrder;
import com.kmhoon.app.repository.OrderRepository;
import com.kmhoon.app.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Optional<OrderEntity> addOrder(@Valid NewOrder newOrder) {
        if(Strings.isEmpty(newOrder.getCustomerId())) {
            throw new ResourceNotFoundException("부정확한 고객 ID 입니다.");
        }
        if(Objects.isNull(newOrder.getAddress()) || Strings.isEmpty(newOrder.getAddress().getId())) {
            throw new ResourceNotFoundException("부정확한 주소 ID 입니다.");
        }
        if(Objects.isNull(newOrder.getCard()) || Strings.isEmpty(newOrder.getCard().getId())) {
            throw new ResourceNotFoundException("부정확한 카드 ID 입니다.");
        }
        return orderRepository.insert(newOrder);
    }

    @Transactional(readOnly = true)
    public Iterable<OrderEntity> getOrdersByCustomerId(@NotNull @Valid String customerId) {
        return orderRepository.findByCustomerId(UUID.fromString(customerId));
    }

    @Transactional(readOnly = true)
    public Optional<OrderEntity> getByOrderId(String id) {
        return orderRepository.findById(UUID.fromString(id));
    }
}
