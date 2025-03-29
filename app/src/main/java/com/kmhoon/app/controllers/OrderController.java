package com.kmhoon.app.controllers;

import com.kmhoon.app.OrderApi;
import com.kmhoon.app.hateoas.OrderRepresentationModelAssembler;
import com.kmhoon.app.model.NewOrder;
import com.kmhoon.app.model.Order;
import com.kmhoon.app.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OrderController implements OrderApi {

    private final OrderRepresentationModelAssembler assembler;
    private final OrderService service;


//    @Override
//    public ResponseEntity<Order> addOrder(NewOrder newOrder) {
//        return service.addOrder(newOrder)
//                .map(assembler::toModel)
//                .map(ResponseEntity::ok)
//                .orElse(notFound().build());
//    }
//
//    @Override
//    public ResponseEntity<List<Order>> getOrdersByCustomerId(String customerId) {
//        return ok(assembler.toListModel(service.getOrdersByCustomerId(customerId)));
//    }
//
//    @Override
//    public ResponseEntity<Order> getByOrderId(String id) {
//        return service.getByOrderId(id)
//                .map(assembler::toModel)
//                .map(ResponseEntity::ok)
//                .orElse(notFound().build());
//    }


}
