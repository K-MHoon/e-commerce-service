package com.kmhoon.app.hateoas;

import com.kmhoon.app.controllers.OrderController;
import com.kmhoon.app.entity.OrderEntity;
import com.kmhoon.app.model.Order;
import com.kmhoon.app.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderRepresentationModelAssembler extends RepresentationModelAssemblerSupport<OrderEntity, Order> {

    private final UserRepresentationModelAssembler userRepresentationModelAssembler;
    private final AddressRepresentationModelAssembler addressRepresentationModelAssembler;
    private final CardRepresentationModelAssembler cardRepresentationModelAssembler;
    private final ShipmentRepresentationModelAssembler shipmentRepresentationModelAssembler;
    private final ItemService itemService;

    public OrderRepresentationModelAssembler(UserRepresentationModelAssembler userRepresentationModelAssembler, AddressRepresentationModelAssembler addressRepresentationModelAssembler, CardRepresentationModelAssembler cardRepresentationModelAssembler, ShipmentRepresentationModelAssembler shipmentRepresentationModelAssembler, ItemService itemService) {
        super(OrderController.class, Order.class);
        this.userRepresentationModelAssembler = userRepresentationModelAssembler;
        this.addressRepresentationModelAssembler = addressRepresentationModelAssembler;
        this.cardRepresentationModelAssembler = cardRepresentationModelAssembler;
        this.shipmentRepresentationModelAssembler = shipmentRepresentationModelAssembler;
        this.itemService = itemService;
    }

    @Override
    public Order toModel(OrderEntity entity) {
        Order resource = createModelWithId(entity.getId(), entity);
        BeanUtils.copyProperties(entity, resource);
        resource.id(entity.getId().toString())
                .customer(userRepresentationModelAssembler.toModel(entity.getUserEntity()))
                .address(addressRepresentationModelAssembler.toModel(entity.getAddressEntity()))
                .card(cardRepresentationModelAssembler.toModel(entity.getCardEntity()))
                .items(itemService.toModelList(entity.getItems()))
                .date(entity.getOrderDate().toInstant().atOffset(ZoneOffset.UTC));

        return resource.add(linkTo(methodOn(OrderController.class).getByOrderId(entity.getId().toString())).withSelfRel());
    }

    public List<Order> toListModel(Iterable<OrderEntity> entities) {
        if(Objects.isNull(entities)) {
            return List.of();
        }
        return StreamSupport.stream(entities.spliterator(), false).map(this::toModel).toList();
    }
}
