package com.kmhoon.app.hateoas;

import com.kmhoon.app.controllers.CartController;
import com.kmhoon.app.entity.CartEntity;
import com.kmhoon.app.model.Cart;
import com.kmhoon.app.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CartRepresentationModelAssembler extends RepresentationModelAssemblerSupport<CartEntity, Cart> {

    private final ItemService itemService;

    public CartRepresentationModelAssembler(ItemService itemService) {
        super(CartController.class, Cart.class);
        this.itemService = itemService;
    }

    @Override
    public Cart toModel(CartEntity entity) {
        String uid = Objects.isNull(entity.getUser()) ? null : entity.getUser().getId().toString();
        String cid = Objects.isNull(entity.getId()) ? null : entity.getId().toString();
        Cart cart = new Cart();
        BeanUtils.copyProperties(entity, cart);
        cart.id(cid).customerId(uid).items(itemService.toModelList(entity.getItems()));
        cart.add(linkTo(methodOn(CartController.class).getCartByCustomerId(uid)).withSelfRel());
        cart.add(linkTo(methodOn(CartController.class).getCartItemsByCustomerId(uid)).withRel("cart-items"));
        return cart;
    }

    public List<Cart> toListModel(Iterable<CartEntity> entities) {
        if(Objects.isNull(entities)) {
            return List.of();
        }
        return StreamSupport.stream(entities.spliterator(), false).map(this::toModel).toList();
    }
}
