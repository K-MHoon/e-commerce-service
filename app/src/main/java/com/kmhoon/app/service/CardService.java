package com.kmhoon.app.service;

import com.kmhoon.app.entity.CardEntity;
import com.kmhoon.app.entity.UserEntity;
import com.kmhoon.app.model.AddCardReq;
import com.kmhoon.app.repository.CardRepository;
import com.kmhoon.app.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public Mono<Void> deleteCardById(String id) {
        return deleteCardById(UUID.fromString(id));
    }

    public Mono<Void> deleteCardById(UUID id) {
        return cardRepository.deleteById(id);
    }

    public Flux<CardEntity> getAllCards() {
        return cardRepository.findAll();
    }

    public Mono<CardEntity> getCardById(String id) {
        return cardRepository.findById(UUID.fromString(id));
    }

    public Mono<CardEntity> registerCard(Mono<AddCardReq> addCardReq) {
        return addCardReq.map(this::toEntity).flatMap(cardRepository::save);
    }

    private CardEntity toEntity(AddCardReq model) {
        CardEntity cardEntity = new CardEntity();
        BeanUtils.copyProperties(model, cardEntity);
        cardEntity.setNumber(model.getCardNumber());
        cardEntity.setUserId(UUID.fromString(model.getUserId()));
        return cardEntity;
    }
}
