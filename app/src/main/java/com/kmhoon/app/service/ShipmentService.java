package com.kmhoon.app.service;

import com.kmhoon.app.entity.ShipmentEntity;
import com.kmhoon.app.repository.ShipmentRepository;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    public Flux<ShipmentEntity> getShipmentByOrderId(@Min(value = 1L, message = "부정확한 배송 ID 입니다.") String id) {
        return shipmentRepository.getShipmentByOrderId(UUID.fromString(id));
    }
}
