package com.kmhoon.app.repository;

import com.kmhoon.app.entity.AuthorizationEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface AuthorizationEntityRepository extends ReactiveCrudRepository<AuthorizationEntity, UUID> {
}