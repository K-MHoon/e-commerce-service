package com.kmhoon.app.service;

import com.kmhoon.app.entity.ProductEntity;
import com.kmhoon.app.entity.TagEntity;
import com.kmhoon.app.repository.ProductRepository;
import com.kmhoon.app.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final TagRepository tagRepository;
    private BiFunction<ProductEntity, List<TagEntity>, ProductEntity> productTagBiFun = (p, t) -> p.setTags(t);

    public Flux<ProductEntity> getAllProducts() {
        return productRepository.findAll()
                .flatMap(products ->
                        Mono.just(products)
                                .zipWith(tagRepository.findTagsByProductId(products.getId()).collectList())
                                .map(t -> t.getT1().setTags(t.getT2()))
                );
    }

    public Mono<ProductEntity> getProduct(String id) {
        Mono<ProductEntity> product = productRepository.findById(UUID.fromString(id))
                .subscribeOn(Schedulers.boundedElastic());
        Flux<TagEntity> tags = tagRepository.findTagsByProductId(UUID.fromString(id)).subscribeOn(Schedulers.boundedElastic());
        return Mono.zip(product, tags.collectList(), productTagBiFun);
    }
}
