package com.kmhoon.app.controllers;

import com.kmhoon.app.ProductApi;
import com.kmhoon.app.hateoas.ProductRepresentationModelAssembler;
import com.kmhoon.app.model.Product;
import com.kmhoon.app.service.ProductService;
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
public class ProductController implements ProductApi {

    private final ProductService productService;
    private final ProductRepresentationModelAssembler assembler;

    @Override
    public ResponseEntity<Product> getProduct(String id) {
        return productService.getProduct(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @Override
    public ResponseEntity<List<Product>> queryProducts(String tag, String name, Integer page, Integer size) {
        return ok(assembler.toListModel(productService.getAllProducts()));
    }
}
