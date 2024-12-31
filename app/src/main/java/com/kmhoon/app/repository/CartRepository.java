package com.kmhoon.app.repository;

import com.kmhoon.app.entity.CartEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends CrudRepository<CartEntity, UUID> {

    @Query("select c from CartEntity c join c.user u where u.id = ?1")
    Optional<CartEntity> findByCustomerId(UUID customerId);
}