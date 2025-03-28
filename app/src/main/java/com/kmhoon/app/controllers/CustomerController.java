package com.kmhoon.app.controllers;

import com.kmhoon.app.CustomerApi;
import com.kmhoon.app.exceptions.ResourceNotFoundException;
import com.kmhoon.app.hateoas.AddressRepresentationModelAssembler;
import com.kmhoon.app.hateoas.CardRepresentationModelAssembler;
import com.kmhoon.app.hateoas.UserRepresentationModelAssembler;
import com.kmhoon.app.model.Address;
import com.kmhoon.app.model.Card;
import com.kmhoon.app.model.User;
import com.kmhoon.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {

    private final UserRepresentationModelAssembler assembler;
    private final AddressRepresentationModelAssembler addressAssembler;
    private final CardRepresentationModelAssembler cardAssembler;
    private final UserService userService;

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomerById(String id, ServerWebExchange exchange) throws Exception {
        return userService.getCustomerById(id)
                .flatMap(c -> userService.deleteCustomerById(c.getId())
                        .then(Mono.just(status(HttpStatus.ACCEPTED).<Void>build())))
                .switchIfEmpty(Mono.just(notFound().build()));
    }

    @Override
    public Mono<ResponseEntity<Flux<Address>>> getAddressesByCustomerId(String id, ServerWebExchange exchange) throws Exception {
        return Mono.just(ok(userService.getAddressesByCustomerId(id)
                .map(c -> addressAssembler.entityToModel(c, exchange))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("고객 주소를 찾을 수 없습니다.")))));
    }

    @Override
    public Mono<ResponseEntity<Flux<User>>> getAllCustomers(ServerWebExchange exchange) throws Exception {
        return CustomerApi.super.getAllCustomers(exchange);
    }

    @Override
    public Mono<ResponseEntity<Card>> getCardByCustomerId(String id, ServerWebExchange exchange) throws Exception {
        return CustomerApi.super.getCardByCustomerId(id, exchange);
    }

    @Override
    public Mono<ResponseEntity<User>> getCustomerById(String id, ServerWebExchange exchange) throws Exception {
        return CustomerApi.super.getCustomerById(id, exchange);
    }
}
