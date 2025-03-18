package com.kmhoon.app.service;

import com.kmhoon.app.entity.CardEntity;
import com.kmhoon.app.entity.ItemEntity;
import com.kmhoon.app.entity.OrderEntity;
import com.kmhoon.app.model.Card;
import com.kmhoon.app.model.NewOrder;
import com.kmhoon.app.repository.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CardRepository cardRepository;
    private final ItemRepository itemRepository;
    private final ShipmentRepository shipmentRepository;
    private BiFunction<OrderEntity, List<ItemEntity>, OrderEntity> biOrderItems = (o, fi) -> o.setItems(fi);

    public Mono<OrderEntity> addOrder(@Valid Mono<NewOrder> newOrder) {
        return orderRepository.insert(newOrder);
    }

    public Mono<OrderEntity> updateMapping(@Valid OrderEntity orderEntity) {
        return orderRepository.updateMapping(orderEntity);
    }

    public Flux<OrderEntity> getOrdersByCustomerId(String customerId) {
        return orderRepository.findByCustomerId(UUID.fromString(customerId)).flatMap(order ->
                Mono.just(order)
                        .zipWith(userRepository.findById(order.getCustomerId()))
                        .map(t -> t.getT1().setUserEntity(t.getT2()))
                        .zipWith(addressRepository.findById(order.getAddressId()))
                        .map(t -> t.getT1().setAddressEntity(t.getT2()))
                        .zipWith(cardRepository.findById(order.getCardId() != null ? order.getCardId() : UUID.fromString("0a59ba9f-629e-4445-8129-b9bce1985d6a")).defaultIfEmpty(new CardEntity()))
                        .map(t -> t.getT1().setCardEntity(t.getT2()))
                        .zipWith(itemRepository.findByCustomerId(order.getCustomerId()).collectList(), biOrderItems)
                );
    }

    public Mono<OrderEntity> getByOrderId(String id) {
        return orderRepository.findById(UUID.fromString(id)).flatMap(order ->
                Mono.just(order)
                .zipWith(userRepository.findById(order.getCustomerId()))
                .map(t -> t.getT1().setUserEntity(t.getT2()))
                        .zipWith(addressRepository.findById(order.getAddressId()))
                        .map(t -> t.getT1().setAddressEntity(t.getT2()))
                        .zipWith(cardRepository.findById(order.getCardId()))
                        .map(t -> t.getT1().setCardEntity(t.getT2()))
                        .zipWith(itemRepository.findByCustomerId(order.getCustomerId()).collectList(), biOrderItems)
        );
    }

}
