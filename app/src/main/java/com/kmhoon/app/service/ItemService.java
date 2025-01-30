package com.kmhoon.app.service;

import com.kmhoon.app.entity.ItemEntity;
import com.kmhoon.app.entity.ProductEntity;
import com.kmhoon.app.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    public ItemEntity toEntity(Item item) {
        return ItemEntity.builder()
                .product(ProductEntity.builder()
                        .id(UUID.fromString(item.getId()))
                        .build())
                .price(item.getUnitPrice())
                .quantity(item.getQuantity())
                .build();
    }

    public List<ItemEntity> toEntityList(List<Item> items) {
        if(Objects.isNull(items)) {
            return List.of();
        }
        return items.stream().map(this::toEntity).toList();
    }

    public Item toModel(ItemEntity entity) {
        Item i = new Item();
        i.id(entity.getProduct().getId().toString()).unitPrice(entity.getPrice()).quantity(entity.getQuantity());
        return i;
    }

    public List<Item> toModelList(List<ItemEntity> items) {
        if(Objects.isNull(items)) {
            return List.of();
        }
        return items.stream().map(this::toModel).toList();
    }
}
