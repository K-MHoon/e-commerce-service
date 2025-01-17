package com.kmhoon.app.service;

import com.kmhoon.app.entity.ShipmentEntity;
import com.kmhoon.app.repository.ShipmentRepository;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    @Transactional(readOnly = true)
    public Iterable<ShipmentEntity> getShipmentByOrderId(@Min(value = 1L, message = "부정확한 선적 ID 입니다.") String id) {
        return shipmentRepository.findAllById(List.of(UUID.fromString(id)));
    }
}
