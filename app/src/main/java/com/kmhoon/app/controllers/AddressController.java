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

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AddressController implements AddressApi {

    private final AddressService addressService;
    private final AddressRepresentationModelAssembler assembler;

    @Override
    public ResponseEntity<Address> createAddress(AddAddressReq addAddressReq) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(addressService.createAddress(addAddressReq).map(assembler::toModel).get());
    }

    @Override
    public ResponseEntity<Void> deleteAddressesById(String id) {
        addressService.deleteAddressesById(id);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<Address> getAddressesById(String id) {
        return addressService.getAddressesById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<Address>> getAllAddresses() {
        return ResponseEntity.ok(assembler.toListModel(addressService.getAllAddresses()));
    }
}
