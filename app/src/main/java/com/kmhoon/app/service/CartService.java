package com.kmhoon.app.service;

import com.kmhoon.app.entity.CartEntity;
import com.kmhoon.app.entity.ItemEntity;
import com.kmhoon.app.entity.UserEntity;
import com.kmhoon.app.exceptions.GenericAlreadyExistsException;
import com.kmhoon.app.exceptions.ResourceNotFoundException;
import com.kmhoon.app.model.Item;
import com.kmhoon.app.repository.CartRepository;
import com.kmhoon.app.repository.ItemRepository;
import com.kmhoon.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;
    private final ItemRepository itemRepository;

    private BiFunction<CartEntity, ItemEntity, CartEntity> cartEntityBiFun = (c, i) -> {
        c.getItems().add(i);
        return c;
    };

    private BiFunction<CartEntity, List<ItemEntity>, CartEntity> cartItemBiFun = CartEntity::setItems;

    private BiFunction<CartEntity, UserEntity, CartEntity> cartUserBiFun = CartEntity::setUser;

    @Transactional
    public Flux<Item> addCartItemsByCustomerId(CartEntity cartEntity, Mono<Item> newItem) {
        final List<ItemEntity> cartItems = cartEntity.getItems();
        return newItem.flatMap(ni -> {
            long countExisting = cartItems.stream().filter(i -> i.getProductId().equals(UUID.fromString(ni.getId()))).count();
            if(countExisting == 1) {
                return Mono.error(new GenericAlreadyExistsException(String.format("요청한 아이템 (%s)은 이미 존재하는 카트 입니다.", ni.getId())));
            }
            return itemRepository.save(itemService.toEntity(ni)).flatMap(i -> itemRepository.saveMapping(cartEntity.getId(), i.getId()).then(
                    getUpdatedList(cartItems, i)
            ));
        }).flatMapMany(Flux::fromIterable);
    }

    public Flux<Item> addOrReplaceItemsByCustomerId(CartEntity cartEntity, Mono<Item> newItem) {
        final List<ItemEntity> cartItems = cartEntity.getItems();
        return newItem.flatMap(ni -> {
            List<ItemEntity> existing = cartItems.stream().filter(i -> i.getProductId().equals(UUID.fromString(ni.getId()))).toList();
            if(existing.size() == 1) {
                existing.get(0).setPrice(ni.getUnitPrice()).setQuantity(ni.getQuantity());
                return itemRepository.save(existing.get(0)).flatMap(i -> getUpdatedList(
                        cartItems.stream().filter(j -> !j.getProductId().equals(UUID.fromString(ni.getId()))).toList(), i
                ));
            }
            return itemRepository.save(itemService.toEntity(ni)).flatMap(i -> itemRepository.saveMapping(cartEntity.getId(), i.getId()).then(
                    getUpdatedList(cartItems, i)
            ));
        }).flatMapMany(Flux::fromIterable);
    }

    private Mono<List<Item>> getUpdatedList(List<ItemEntity> cartItems, ItemEntity savedItem) {
        cartItems.add(savedItem);
        return Mono.just(itemService.toModelList(cartItems));
    }

    @Transactional
    public Mono<Void> deleteCart(String customerId, String cartId) {
        Mono<List<UUID>> monoIds = itemRepository.findByCustomerId(UUID.fromString(customerId))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(
                        "장바구니에 있는 아이템을 찾을 수 없습니다. 고객 ID - " + customerId
                )))
                .map(i -> i.getId())
                .collectList().cache();
        return monoIds.zipWhen(l -> {
            List<UUID> ids = l.subList(0, l.size());
            return itemRepository.deleteCartItemJoinById(ids, UUID.fromString(cartId))
                    .then(itemRepository.deleteByIds(ids).subscribeOn(Schedulers.boundedElastic()));
        }).then();
    }

    public Mono<Void> deleteItemFromCart(CartEntity cartEntity, String itemId) {
        List<ItemEntity> items = cartEntity.getItems();
        items = items.stream().filter(i -> i.getProductId().equals(UUID.fromString(itemId))).toList();
        if(items.size() != 1) {
            return Mono.error(new ResourceNotFoundException(
                    "장바구니에 있는 아이템을 찾을 수 없습니다. ID - " + itemId
            ));
        }
        List<UUID> ids = items.stream().map(i -> i.getId()).toList();
        return itemRepository.deleteCartItemJoinById(ids, cartEntity.getId())
                .then(itemRepository.deleteByIds(ids)).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<CartEntity> getCartByCustomerId(String customerId) {
        Mono<CartEntity> cart = cartRepository.findByCustomerId(UUID.fromString(customerId))
                .subscribeOn(Schedulers.boundedElastic());
        Mono<UserEntity> user = userRepository.findById(UUID.fromString(customerId)).subscribeOn(Schedulers.boundedElastic());
        cart = Mono.zip(cart, user, cartUserBiFun);
        Flux<ItemEntity> items = itemRepository.findByCustomerId(UUID.fromString(customerId)).subscribeOn(Schedulers.boundedElastic());
        return Mono.zip(cart, items.collectList(), cartItemBiFun);
    }
}
