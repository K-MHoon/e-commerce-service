package com.kmhoon.app.controllers;

import com.kmhoon.app.CartApi;
import com.kmhoon.app.hateoas.CartRepresentationModelAssembler;
import com.kmhoon.app.model.Cart;
import com.kmhoon.app.model.Item;
import com.kmhoon.app.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CartController implements CartApi {

    private final CartService cartService;
    private final CartRepresentationModelAssembler assembler;

    @Override
    public ResponseEntity<List<Item>> addCartItemsByCustomerId(String customerId, Item item) {
        return ok(cartService.addCartItemsByCustomerId(customerId, item));
    }

    @Override
    public ResponseEntity<List<Item>> addOrReplaceItemsByCustomerId(String customerId, Item item) {
        return ok(cartService.addOrReplaceItemsByCustomerId(customerId, item));
    }

    @Override
    public ResponseEntity<Void> deleteCart(String customerId) {
        cartService.deleteCart(customerId);
        return accepted().build();
    }

    @Override
    public ResponseEntity<Void> deleteItemFromCart(String customerId, String itemId) {
        cartService.deleteItemFromCart(customerId, itemId);
        return accepted().build();
    }

    @Override
    public ResponseEntity<Cart> getCartByCustomerId(String customerId)  {
        return ok(assembler.toModel(cartService.getCartByCustomerId(customerId)));
    }

    @Override
    public ResponseEntity<List<Item>> getCartItemsByCustomerId(String customerId) {
        return ok(cartService.getCartItemsByCustomerId(customerId));
    }

    @Override
    public ResponseEntity<Item> getCartItemsByItemId(String customerId, String itemId) {
        return ok(cartService.getCartItemsByItemId(customerId, itemId));
    }
}
