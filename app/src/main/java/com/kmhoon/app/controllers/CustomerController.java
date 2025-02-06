package com.kmhoon.app.controllers;

import com.kmhoon.app.CustomerApi;
import com.kmhoon.app.hateoas.AddressRepresentationModelAssembler;
import com.kmhoon.app.hateoas.CardRepresentationModelAssembler;
import com.kmhoon.app.hateoas.UserRepresentationModelAssembler;
import com.kmhoon.app.model.Address;
import com.kmhoon.app.model.Card;
import com.kmhoon.app.model.User;
import com.kmhoon.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

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
    public ResponseEntity<Void> deleteCustomerById(String id) {
        userService.deleteCustomerById(id);
        return accepted().build();
    }

    @Override
    public ResponseEntity<List<Address>> getAddressesByCustomerId(String id) {
        return userService.getAddressesByCustomerId(id)
                .map(addressAssembler::toListModel)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @Override
    public ResponseEntity<List<User>> getAllCustomers() {
        return ok(assembler.toListModel(userService.getAllCustomers()));
    }

    @Override
    public ResponseEntity<Card> getCardByCustomerId(String id) {
        return userService.getCardByCustomerId(id)
                .map(cardAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @Override
    public ResponseEntity<User> getCustomerById(String id) {
        return userService.getCustomerById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }
}
