package com.kmhoon.app.service;

import com.kmhoon.app.entity.CardEntity;
import com.kmhoon.app.entity.UserEntity;
import com.kmhoon.app.model.AddCardReq;
import com.kmhoon.app.repository.CardRepository;
import com.kmhoon.app.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Transactional
    public void deleteCardById(String id) {
        cardRepository.deleteById(UUID.fromString(id));
    }

    @Transactional(readOnly = true)
    public Iterable<CardEntity> getAllCards() {
        return cardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<CardEntity> getCardById(String id) {
        return cardRepository.findById(UUID.fromString(id));
    }

    @Transactional
    public Optional<CardEntity> registerCard(@Valid AddCardReq addCardReq) {
        return Optional.of(cardRepository.save(toEntity(addCardReq)));
    }

    private CardEntity toEntity(AddCardReq m) {
        Optional<UserEntity> user = userRepository.findById(UUID.fromString(m.getUserId()));
        return CardEntity.builder()
                .user(user.orElse(null))
                .number(m.getCardNumber())
                .cvv(m.getCvv())
                .expires(m.getExpires())
                .build();
    }
}
