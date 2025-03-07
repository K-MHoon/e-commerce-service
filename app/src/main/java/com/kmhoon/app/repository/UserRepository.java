package com.kmhoon.app.repository;

import com.kmhoon.app.entity.AddressEntity;
import com.kmhoon.app.entity.CardEntity;
import com.kmhoon.app.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, UUID> {

    @Query("select a.* from ecomm.user u, ecomm.address a, ecomm.user_address ua where u.id=ua.user_id and a.id=ua.address_id and u.id = :id")
    Flux<AddressEntity> getAddressesByCustomerId(UUID id);

    @Query("select c.* from ecomm.user u, ecomm.card c where u.id=c.user_id and u.id = :id")
    Mono<CardEntity> findCardByCustomerId(UUID id);
}