package com.kmhoon.app.repository;

import com.kmhoon.app.entity.OrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends CrudRepository<OrderEntity, UUID>, OrderRepositoryExt {

    @Query("select o from OrderEntity o join o.userEntity u where u.id = ?1")
    List<OrderEntity> findByCustomerId(UUID customerId);
}