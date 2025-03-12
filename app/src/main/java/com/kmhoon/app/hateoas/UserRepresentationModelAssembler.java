package com.kmhoon.app.hateoas;

import com.kmhoon.app.controllers.CustomerController;
import com.kmhoon.app.entity.ShipmentEntity;
import com.kmhoon.app.entity.UserEntity;
import com.kmhoon.app.model.Shipment;
import com.kmhoon.app.model.User;
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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserRepresentationModelAssembler implements ReactiveRepresentationModelAssembler<UserEntity, User>, HateoasSupport {

    private static String serverUri = null;

    private String getServerUri(@Nullable ServerWebExchange exchange) {
        if(Strings.isBlank(serverUri)) {
            serverUri = getUriComponentsBuilder(exchange).toString();
        }
        return serverUri;
    }

    @Override
    public Mono<User> toModel(UserEntity entity, ServerWebExchange exchange) {
        return Mono.just(entityToModel(entity, exchange));
    }

    public User entityToModel(UserEntity entity, ServerWebExchange exchange) {
        User resource = new User();
        if(Objects.isNull(entity)) {
            return resource;
        }
        BeanUtils.copyProperties(entity, resource);
        resource.setId(entity.getId().toString());
        String serverUri = getServerUri(exchange);
        resource.add(Link.of(String.format("%s/api/v1/customers", serverUri)).withRel("customers"));
        resource.add(Link.of(String.format("%s/api/v1/customers/%s", serverUri, entity.getId())).withSelfRel());
        resource.add(Link.of(String.format("%s/api/v1/customers/%s/addresses", serverUri, entity.getId())).withRel("self_addresses"));
        return resource;
    }

    public User getModel(Mono<User> m, ServerWebExchange exchange) {
        AtomicReference<User> model = new AtomicReference<>();
        m.cache().subscribe(model::set);
        return model.get();
    }

    public Flux<User> toListModel(Flux<UserEntity> entities, ServerWebExchange exchange) {
        if(Objects.isNull(entities)) {
            return Flux.empty();
        }
        return Flux.from(entities.map(e -> entityToModel(e, exchange)));
    }
}
