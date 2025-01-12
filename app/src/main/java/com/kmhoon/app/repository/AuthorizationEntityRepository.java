package com.kmhoon.app.repository;

import com.kmhoon.app.entity.AuthorizationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AuthorizationEntityRepository extends CrudRepository<AuthorizationEntity, UUID> {
}