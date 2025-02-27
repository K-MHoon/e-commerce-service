package com.kmhoon.app.repository;

import com.kmhoon.app.entity.PaymentEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface PaymentRepository extends ReactiveCrudRepository<PaymentEntity, UUID> {
}