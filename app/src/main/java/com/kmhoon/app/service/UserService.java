package com.kmhoon.app.service;

import com.kmhoon.app.entity.AddressEntity;
import com.kmhoon.app.entity.CardEntity;
import com.kmhoon.app.entity.UserEntity;
import com.kmhoon.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Mono<Void> deleteCustomerById(String id) {
        return deleteCustomerById(UUID.fromString(id));
    }

    public Mono<Void> deleteCustomerById(UUID id) {
        return userRepository.deleteById(id).then();
    }

    public Flux<AddressEntity> getAddressesByCustomerId(String id) {
        return userRepository.getAddressesByCustomerId(UUID.fromString(id));
    }

    public Flux<UserEntity> getAllCustomers() {
        return userRepository.findAll();
    }

    public Mono<CardEntity> getCardByCustomerId(String id) {
        return userRepository.findCardByCustomerId(UUID.fromString(id));
    }

    public Mono<UserEntity> getCustomerById(String id) {
        return userRepository.findById(UUID.fromString(id));
    }
}
