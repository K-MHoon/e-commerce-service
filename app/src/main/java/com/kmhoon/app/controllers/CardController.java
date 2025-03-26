package com.kmhoon.app.controllers;

import com.kmhoon.app.CardApi;
import com.kmhoon.app.entity.UserEntity;
import com.kmhoon.app.exceptions.CardAlreadyExistsException;
import com.kmhoon.app.exceptions.CustomerNotFoundException;
import com.kmhoon.app.hateoas.CardRepresentationModelAssembler;
import com.kmhoon.app.model.AddCardReq;
import com.kmhoon.app.model.Card;
import com.kmhoon.app.service.CardService;
import com.kmhoon.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CardController implements CardApi {

    private final CardService cardService;
    private final CardRepresentationModelAssembler assembler;
    private final UserService userService;

    @Override
    public Mono<ResponseEntity<Void>> deleteCardById(String id, ServerWebExchange exchange) throws Exception {
        return cardService.getCardById(id)
                .flatMap(c -> cardService.deleteCardById(c.getId()))
                .then(Mono.just(status(HttpStatus.ACCEPTED).<Void>build()))
                .switchIfEmpty(Mono.just(notFound().build()));
    }

    @Override
    public Mono<ResponseEntity<Flux<Card>>> getAllCards(ServerWebExchange exchange) throws Exception {
        return Mono.just(ok(assembler.toListModel(cardService.getAllCards(), exchange)));
    }

    @Override
    public Mono<ResponseEntity<Card>> getCardById(String id, ServerWebExchange exchange) throws Exception {
        return cardService.getCardById(id).map(c -> assembler.entityToModel(c, exchange)).map(ResponseEntity::ok).defaultIfEmpty(notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Card>> registerCard(Mono<AddCardReq> addCardReq, ServerWebExchange exchange) throws Exception {
        Mono<AddCardReq> mono = addCardReq.cache();
        return validate(mono)
                .flatMap(d -> userService.getCardByCustomerId(d.getId().toString())
                        .flatMap(card -> {
                            if(Objects.isNull(card.getId())) {
                                return cardService.registerCard(mono)
                                        .map(ce -> status(HttpStatus.CREATED)
                                                .body(assembler.entityToModel(ce, exchange)));
                            } else {
                                return Mono.error(() -> new CardAlreadyExistsException(" 사용자 ID = " + d.getId()));
                            }
                        })
                        .switchIfEmpty(cardService.registerCard(mono).map(ce -> status(HttpStatus.CREATED).body(assembler.entityToModel(ce, exchange)))));
    }

    private Mono<UserEntity> validate(Mono<AddCardReq> addCardReq) {
        return addCardReq.flatMap(req -> userService.getCustomerById(req.getId()))
                .switchIfEmpty(Mono.error(new CustomerNotFoundException("데이터를 확인해주세요")));
    }
}
