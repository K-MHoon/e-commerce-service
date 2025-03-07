package com.kmhoon.app.repository;

import com.kmhoon.app.entity.AddressEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface AddressRepository extends ReactiveCrudRepository<AddressEntity, UUID> {
}
