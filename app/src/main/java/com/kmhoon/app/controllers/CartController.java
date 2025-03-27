package com.kmhoon.app.controllers;

import com.kmhoon.app.CartApi;
import com.kmhoon.app.hateoas.CartRepresentationModelAssembler;
import com.kmhoon.app.model.Cart;
import com.kmhoon.app.model.Item;
import com.kmhoon.app.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.status;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CartController implements CartApi {

    private final CartService cartService;
    private final CartRepresentationModelAssembler assembler;

    @Override
    public Mono<ResponseEntity<Flux<Item>>> addCartItemsByCustomerId(String customerId, Mono<Item> item, ServerWebExchange exchange) throws Exception {
        return cartService.getCartByCustomerId(customerId)
                .map(a -> status(HttpStatus.CREATED)
                        .body(cartService.addCartItemsByCustomerId(a , item.cache())))
                .switchIfEmpty(Mono.just(notFound().build()));
    }

    @Override
    public Mono<ResponseEntity<Flux<Item>>> addOrReplaceItemsByCustomerId(String customerId, Mono<Item> item, ServerWebExchange exchange) throws Exception {
        return cartService.getCartByCustomerId(customerId)
                .map(a -> status(HttpStatus.CREATED).body(cartService.addOrReplaceItemsByCustomerId(a, item.cache())))
                .switchIfEmpty(Mono.just(notFound().build()));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCart(String customerId, ServerWebExchange exchange) throws Exception {
        return cartService.getCartByCustomerId(customerId)
                .flatMap(a -> cartService.deleteCart(a.getUser().getId().toString(), a.getId().toString())
                        .then(Mono.just(status(HttpStatus.ACCEPTED).<Void>build())))
                .switchIfEmpty(Mono.just(notFound().build()));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteItemFromCart(String customerId, String itemId, ServerWebExchange exchange) throws Exception {
        return cartService.getCartByCustomerId(customerId)
                .flatMap(a -> cartService.deleteItemFromCart(a, itemId.trim())
                        .then(Mono.just(status(HttpStatus.ACCEPTED).<Void>build())))
                .switchIfEmpty(Mono.just(notFound().build()));
    }

    @Override
    public Mono<ResponseEntity<Cart>> getCartByCustomerId(String customerId, ServerWebExchange exchange) throws Exception {
        return cartService.getCartByCustomerId(customerId)
                .map(c -> assembler.entityToModel(c, exchange))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Flux<Item>>> getCartItemsByCustomerId(String customerId, ServerWebExchange exchange) throws Exception {
        return cartService.getCartByCustomerId(customerId)
                .map(a -> Flux.fromIterable(assembler.itemFromEntities(a.getItems())))
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(notFound().build()));
    }

    @Override
    public Mono<ResponseEntity<Item>> getCartItemsByItemId(String customerId, String itemId, ServerWebExchange exchange) throws Exception {
        return cartService.getCartByCustomerId(customerId)
                .map(cart -> assembler.itemFromEntities(cart.getItems().stream().filter(i -> i.getProductId().toString().equals(itemId.trim())).toList()).get(0))
                .map(ResponseEntity::ok)
                .onErrorReturn(notFound().build())
                .switchIfEmpty(Mono.just(notFound().build()));
    }
}
