package com.kmhoon.app.repository;

import com.kmhoon.app.entity.OrderEntity;
import com.kmhoon.app.model.NewOrder;
import reactor.core.publisher.Mono;

public interface OrderRepositoryExt  {
    Mono<OrderEntity> insert(Mono<NewOrder> m);
    Mono<OrderEntity> updateMapping(OrderEntity orderEntity);
}