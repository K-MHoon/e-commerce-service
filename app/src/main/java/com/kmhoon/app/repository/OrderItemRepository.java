package com.kmhoon.app.repository;

import com.kmhoon.app.entity.OrderItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface OrderItemRepository extends ReactiveCrudRepository<OrderItemEntity, UUID> {
}