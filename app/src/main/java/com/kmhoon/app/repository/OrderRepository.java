package com.kmhoon.app.repository;

import java.util.UUID;

import com.kmhoon.app.entity.OrderEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<OrderEntity, UUID>, OrderRepositoryExt {

    @Query("select o.* from ecomm.orders o join ecomm.user u on o.customer_id = u.id where u.id = :custId")
    Flux<OrderEntity> findByCustomerId(UUID custId);
}