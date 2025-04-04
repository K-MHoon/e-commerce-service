package com.kmhoon.app.repository;

import com.kmhoon.app.entity.CartEntity;
import com.kmhoon.app.entity.ItemEntity;
import com.kmhoon.app.entity.OrderEntity;
import com.kmhoon.app.model.NewOrder;
import io.r2dbc.spi.ConnectionFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.data.util.Pair.toMap;

@Repository
@Transactional
@RequiredArgsConstructor
public class OrderRepositoryExtImpl implements OrderRepositoryExt {

    private final ConnectionFactory connectionFactory;
    private final DatabaseClient dbClient;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private OrderItemRepository orderItemRepository;

//    private OrderEntity toEntity(NewOrder order, CartEntity cart) {
//        OrderEntity orderEntity = new OrderEntity();
//        BeanUtils.copyProperties(order, orderEntity);
//        orderEntity.setUserEntity(cart.getUser())
//        .setCardId(cart.getId())
//        .setItems(cart.getItems())
//        .setCustomerId(UUID.fromString(order.getCustomerId()))
//        .setAddressId(UUID.fromString(order.getAddress().getId()))
//        .setOrderDate(Timestamp.from(Instant.now()))
//                .setTotal());
//    }

    @Override
    public Mono<OrderEntity> insert(Mono<NewOrder> m) {
        return null;
    }

    @Override
    public Mono<OrderEntity> updateMapping(OrderEntity orderEntity) {
        return null;
    }
}