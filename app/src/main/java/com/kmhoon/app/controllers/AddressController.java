package com.kmhoon.app.controllers;

import com.kmhoon.app.AddressApi;
import com.kmhoon.app.hateoas.AddressRepresentationModelAssembler;
import com.kmhoon.app.model.AddAddressReq;
import com.kmhoon.app.model.Address;
import com.kmhoon.app.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AddressController implements AddressApi {

    private final AddressService addressService;
    private final AddressRepresentationModelAssembler assembler;

    @Override
    public Mono<ResponseEntity<Address>> createAddress(Mono<AddAddressReq> addAddressReq, ServerWebExchange exchange) throws Exception {
        return addressService.createAddress(addAddressReq)
                .map(a -> assembler.entityToModel(a, exchange)).map(e -> status(HttpStatus.CREATED).body(e));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteAddressesById(String id, ServerWebExchange exchange) throws Exception {
        return addressService.getAddressesById(id)
                .flatMap(a -> addressService.deleteAddressesById(a.getId()).then(Mono.just(status(HttpStatus.ACCEPTED).<Void>build())))
                .switchIfEmpty(Mono.just(notFound().build()));
    }

    @Override
    public Mono<ResponseEntity<Address>> getAddressesById(String id, ServerWebExchange exchange) throws Exception {
        return addressService.getAddressesById(id).map(a -> assembler.entityToModel(a, exchange))
                .map(ResponseEntity::ok).defaultIfEmpty(notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Flux<Address>>> getAllAddresses(ServerWebExchange exchange) throws Exception {
        return Mono.just(ok(assembler.toListModel(addressService.getAllAddresses(), exchange)));
    }

}
