package com.kmhoon.app.hateoas;

import com.kmhoon.app.entity.OrderEntity;
import com.kmhoon.app.model.Order;
import com.kmhoon.app.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.reactive.ReactiveRepresentationModelAssembler;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZoneOffset;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class OrderRepresentationModelAssembler implements ReactiveRepresentationModelAssembler<OrderEntity, Order>, HateoasSupport {

    private static String serverUri = null;
    private final UserRepresentationModelAssembler uAssembler;
    private final AddressRepresentationModelAssembler aAssembler;
    private final CardRepresentationModelAssembler cAssembler;
    private final ShipmentRepresentationModelAssembler sAssembler;
    private ItemService itemService;

    private String getServerUri(@Nullable ServerWebExchange exchange) {
        if(Strings.isBlank(serverUri)) {
            serverUri = getUriComponentsBuilder(exchange).toUriString();
        }
        return serverUri;
    }

    @Override
    public Mono<Order> toModel(OrderEntity entity, ServerWebExchange exchange) {
        return null;
    }

    public Order entityToModel(OrderEntity entity, ServerWebExchange exchange) {
        Order resource = new Order();
        if(Objects.isNull(entity)) {
            return resource;
        }
        BeanUtils.copyProperties(entity, resource);
        resource.id(entity.getId().toString())
                .customer(uAssembler.entityToModel(entity.getUserEntity(), exchange))
                .address(aAssembler.entityToModel(entity.getAddressEntity(), exchange))
                .card(cAssembler.entityToModel(entity.getCardEntity(), exchange))
                .items(itemService.toModelList(entity.getItems()))
                .date(entity.getOrderDate().toInstant().atOffset(ZoneOffset.UTC));
        String serverUri = getServerUri(exchange);
        resource.add(Link.of(String.format("%s/api/v/orders", serverUri)).withRel("orders"));
        resource.add(Link.of(String.format("%s/api/v/orders/%s", serverUri, entity.getId())).withSelfRel());
        return resource;
    }

    public Flux<Order> toListModel(Flux<OrderEntity> entities, ServerWebExchange exchange) {
        if(Objects.isNull(entities)) {
            return Flux.empty();
        }
        return Flux.from(entities.map(e -> entityToModel(e, exchange)));
    }
}
