package com.kmhoon.app.hateoas;

import com.kmhoon.app.controllers.ShipmentController;
import com.kmhoon.app.entity.ProductEntity;
import com.kmhoon.app.entity.ShipmentEntity;
import com.kmhoon.app.model.Product;
import com.kmhoon.app.model.Shipment;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.reactive.ReactiveRepresentationModelAssembler;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ShipmentRepresentationModelAssembler implements ReactiveRepresentationModelAssembler<ShipmentEntity, Shipment>, HateoasSupport {

    private static String serverUri = null;

    private String getServerUri(@Nullable ServerWebExchange exchange) {
        if(Strings.isBlank(serverUri)) {
            serverUri = getUriComponentsBuilder(exchange).toUriString();
        }
        return serverUri;
    }

    @Override
    public Mono<Shipment> toModel(ShipmentEntity entity, ServerWebExchange exchange) {
        return Mono.just(entityToModel(entity, exchange));
    }

    public Shipment entityToModel(ShipmentEntity entity, ServerWebExchange exchange) {
        Shipment resource = new Shipment();
        if(Objects.isNull(entity)) {
            return resource;
        }
        BeanUtils.copyProperties(entity, resource);
        String serverUri = getServerUri(exchange);
        resource.add(Link.of(String.format("%s/api/v1/shipping/%s", serverUri, entity.getId())).withSelfRel());
        return resource;
    }

    public Flux<Shipment> toListModel(Flux<ShipmentEntity> entities, ServerWebExchange exchange) {
        if(Objects.isNull(entities)) {
            return Flux.empty();
        }
        return Flux.from(entities.map(e -> entityToModel(e, exchange)));
    }
}
