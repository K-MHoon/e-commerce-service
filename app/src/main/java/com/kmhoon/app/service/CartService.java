package com.kmhoon.app.service;

import com.kmhoon.app.entity.CartEntity;
import com.kmhoon.app.entity.ItemEntity;
import com.kmhoon.app.entity.UserEntity;
import com.kmhoon.app.exceptions.CustomerNotFoundException;
import com.kmhoon.app.exceptions.GenericAlreadyExistsException;
import com.kmhoon.app.exceptions.ItemNotFoundException;
import com.kmhoon.app.model.Item;
import com.kmhoon.app.repository.CartRepository;
import com.kmhoon.app.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;

    @Transactional
    public List<Item> addCartItemsByCustomerId(String customerId, @Valid Item item) {
        CartEntity entity = getCartByCustomerId(customerId);
        boolean isPresent = entity.getItems().stream()
                .anyMatch(i -> i.getProduct().getId().equals(UUID.fromString(item.getId())));
        if(isPresent) {
            throw new GenericAlreadyExistsException(String.format("아이템 ID(%s)가 이미 존재합니다.", item.getId()));
        }
        entity.getItems().add(itemService.toEntity(item));
        return itemService.toModelList(cartRepository.save(entity).getItems());
    }

    @Transactional
    public List<Item> addOrReplaceItemsByCustomerId(String customerId, @Valid Item item) {
        CartEntity cart = getCartByCustomerId(customerId);
        Optional<ItemEntity> itemOpt = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(UUID.fromString(item.getId())))
                .findAny();

        if (itemOpt.isEmpty()) {
            cart.getItems().add(itemService.toEntity(item));
        } else {
            ItemEntity itemEntity = itemOpt.get();
            itemEntity.updateQuantity(item.getQuantity());
            itemEntity.updatePrice(item.getUnitPrice());
        }

        cartRepository.save(cart);

        return itemService.toModelList(cart.getItems());
    }

    @Transactional(readOnly = true)
    public CartEntity getCartByCustomerId(String customerId) {
        return cartRepository.findByCustomerId(UUID.fromString(customerId))
                .orElse(CartEntity.builder()
                        .user(getUserByCustomerId(customerId))
                        .build());
    }


    private UserEntity getUserByCustomerId(String customerId) {
        return userRepository.findById(UUID.fromString(customerId)).orElseThrow(() -> new CustomerNotFoundException(String.format(" - %s", customerId)));
    }

    @Transactional
    public void deleteCart(String customerId) {
        CartEntity entity = getCartByCustomerId(customerId);
        cartRepository.deleteById(entity.getId());
    }

    @Transactional
    public void deleteItemFromCart(String customerId, String itemId) {
        CartEntity entity = getCartByCustomerId(customerId);
        List<ItemEntity> updatedItems = entity.getItems().stream()
                .filter(i -> !i.getProduct().getId().equals(UUID.fromString(itemId)))
                .toList();
        entity.updateItems(updatedItems);
        cartRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<Item> getCartItemsByCustomerId(String customerId) {
        CartEntity entity = getCartByCustomerId(customerId);
        return itemService.toModelList(entity.getItems());
    }

    @Transactional(readOnly = true)
    public Item getCartItemsByItemId(String customerId, String itemId) {
        CartEntity entity = getCartByCustomerId(customerId);
        Optional<ItemEntity> itemOpt = entity.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(UUID.fromString(itemId)))
                .findAny();
        if(itemOpt.isEmpty()) {
            throw new ItemNotFoundException(String.format(" - %s", itemId));
        }
        return itemService.toModel(itemOpt.get());
    }
}
