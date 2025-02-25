package com.kmhoon.app.repository;

import com.kmhoon.app.entity.CardEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface CardRepository extends ReactiveCrudRepository<CardEntity, UUID> {
}