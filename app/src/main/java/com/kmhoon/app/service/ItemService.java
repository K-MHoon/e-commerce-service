package com.kmhoon.app.service;

import com.kmhoon.app.entity.CartEntity;
import com.kmhoon.app.entity.ItemEntity;
import com.kmhoon.app.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    public Mono<ItemEntity> toEntity(Mono<Item> model) {
        return model.map(m -> ItemEntity.builder().price(m.getUnitPrice()).quantity(m.getQuantity()).build());
    }

    public Mono<List<Item>> fluxToList(Flux<ItemEntity> items) {
        if(Objects.isNull(items)) {
            return Mono.just(Collections.emptyList());
        }
        return items.map(this::toModel).collectList();
    }

    public Flux<Item> toItemFlux(Mono<CartEntity> cart) {
        if(Objects.isNull(cart)) {
            return Flux.empty();
        }
        return cart.flatMapMany(c -> toModelFlux(c.getItems()));
    }

    public ItemEntity toEntity(Item m) {
        return ItemEntity.builder()
                .productId(UUID.fromString(m.getId()))
                .price(m.getUnitPrice())
                .quantity(m.getQuantity())
                .build();
    }

    public List<ItemEntity> toEntityList(List<Item> items) {
        if(Objects.isNull(items)) {
            return Collections.emptyList();
        }
        return items.stream().map(this::toEntity).toList();
    }

    public Item toModel(ItemEntity e) {
        Item item = new Item();
        item.id(e.getProductId().toString())
                .unitPrice(e.getPrice())
                .quantity(e.getQuantity());
        return item;
    }

    public List<Item> toModelList(List<ItemEntity> items) {
        if(Objects.isNull(items)) {
            return Collections.emptyList();
        }
        return items.stream().map(this::toModel).toList();
    }

    public Flux<Item> toModelFlux(List<ItemEntity> items) {
        if (Objects.isNull(items)) {
            return Flux.empty();
        }
        return Flux.fromIterable(items.stream().map(this::toModel).toList());
    }
}
