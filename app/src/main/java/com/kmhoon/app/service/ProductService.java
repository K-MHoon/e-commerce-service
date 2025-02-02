package com.kmhoon.app.service;

import com.kmhoon.app.entity.ProductEntity;
import com.kmhoon.app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Iterable<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ProductEntity> getProduct(String id ){
        return productRepository.findById(UUID.fromString(id));
    }
}
