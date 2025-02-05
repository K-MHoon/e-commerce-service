package com.kmhoon.app.controllers;

import com.kmhoon.app.CardApi;
import com.kmhoon.app.hateoas.CardRepresentationModelAssembler;
import com.kmhoon.app.model.AddCardReq;
import com.kmhoon.app.model.Card;
import com.kmhoon.app.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CardController implements CardApi {

    private final CardService cardService;
    private final CardRepresentationModelAssembler assembler;

    @Override
    public ResponseEntity<Void> deleteCardById(String id) {
        cardService.deleteCardById(id);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<List<Card>> getAllCards() {
        return ResponseEntity.ok(assembler.toListModel(cardService.getAllCards()));
    }

    @Override
    public ResponseEntity<Card> getCardById(String id) {
        return cardService.getCardById(id).map(assembler::toModel)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Card> registerCard(AddCardReq addCardReq) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.registerCard(addCardReq).map(assembler::toModel).get());
    }
}
