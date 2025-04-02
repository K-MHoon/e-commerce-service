package com.kmhoon.app.controllers;

import com.kmhoon.app.ShipmentApi;
import com.kmhoon.app.hateoas.ShipmentRepresentationModelAssembler;
import com.kmhoon.app.model.Shipment;
import com.kmhoon.app.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ShipmentController implements ShipmentApi {

    private final ShipmentService service;
    private final ShipmentRepresentationModelAssembler assembler;

//    @Override
//    public ResponseEntity<List<Shipment>> getShipmentByOrderId(String id) {
//        return ResponseEntity.ok(assembler.toListModel(service.getShipmentByOrderId(id)));
//    }
}
