package com.kmhoon.app.service;

import com.kmhoon.app.entity.AddressEntity;
import com.kmhoon.app.entity.CardEntity;
import com.kmhoon.app.entity.UserEntity;
import com.kmhoon.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void deleteCustomerById(String id) {
        userRepository.deleteById(UUID.fromString(id));
    }

    @Transactional(readOnly = true)
    public Optional<Iterable<AddressEntity>> getAddressesByCustomerId(String id) {
        return userRepository.findById(UUID.fromString(id)).map(UserEntity::getAddresses);
    }

    @Transactional(readOnly = true)
    public Iterable<UserEntity> getAllCustomers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<CardEntity> getCardByCustomerId(String id) {
        return Optional.of(userRepository.findById(UUID.fromString(id)).map(UserEntity::getCards).get().get(0));
    }

    @Transactional(readOnly = true)
    public Optional<UserEntity> getCustomerById(String id) {
        return userRepository.findById(UUID.fromString(id));
    }
}
