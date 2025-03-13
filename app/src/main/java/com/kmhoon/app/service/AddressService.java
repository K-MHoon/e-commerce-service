package com.kmhoon.app.service;

import com.kmhoon.app.entity.AddressEntity;
import com.kmhoon.app.model.AddAddressReq;
import com.kmhoon.app.model.Address;
import com.kmhoon.app.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public Mono<AddressEntity> createAddress(Mono<AddAddressReq> addAddressReq) {
        return addAddressReq.map(this::toEntity).flatMap(addressRepository::save);
    }

    public Mono<Void> deleteAddressesById(String id) {
        return deleteAddressesById(UUID.fromString(id));
    }

    public Mono<Void> deleteAddressesById(UUID id) {
        return addressRepository.deleteById(id).then();
    }

    public Mono<AddressEntity> getAddressesById(String id) {
        return addressRepository.findById(UUID.fromString(id));
    }

    public Flux<AddressEntity> getAllAddresses() {
        return addressRepository.findAll();
    }

    private AddressEntity toEntity(AddAddressReq model) {
        AddressEntity entity = new AddressEntity();
        BeanUtils.copyProperties(model, entity);
        return entity;
    }

    private AddressEntity toEntity(Mono<AddAddressReq> monoModel) {
        AddressEntity entity = new AddressEntity();
        monoModel.cache().subscribe(model -> BeanUtils.copyProperties(model, entity));
        return entity;
    }
}
