package com.kmhoon.app.service;

import com.kmhoon.app.entity.AddressEntity;
import com.kmhoon.app.model.AddAddressReq;
import com.kmhoon.app.model.Address;
import com.kmhoon.app.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional
    public Optional<AddressEntity> createAddress(AddAddressReq addAddressReq) {
        return Optional.of(addressRepository.save(toEntity(addAddressReq)));
    }

    @Transactional
    public void deleteAddressesById(String id) {
        addressRepository.deleteById(UUID.fromString(id));
    }

    @Transactional(readOnly = true)
    public Optional<AddressEntity> getAddressesById(String id) {
        return addressRepository.findById(UUID.fromString(id));
    }

    @Transactional(readOnly = true)
    public Iterable<AddressEntity> getAllAddresses() {
        return addressRepository.findAll();
    }

    private AddressEntity toEntity(AddAddressReq model) {
        return AddressEntity.builder()
                .number(model.getNumber())
                .residency(model.getResidency())
                .street(model.getStreet())
                .city(model.getCity())
                .state(model.getState())
                .country(model.getCountry())
                .pincode(model.getPincode())
                .build();
    }
}
