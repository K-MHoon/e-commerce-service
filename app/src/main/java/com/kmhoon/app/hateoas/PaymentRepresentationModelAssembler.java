package com.kmhoon.app.hateoas;

import com.kmhoon.app.entity.PaymentEntity;
import com.kmhoon.app.model.Payment;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.reactive.ReactiveRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class PaymentRepresentationModelAssembler implements ReactiveRepresentationModelAssembler<PaymentEntity, Payment>, HateoasSupport {

    @Override
    public Mono<Payment> toModel(PaymentEntity entity, ServerWebExchange exchange) {
        return Mono.just(entityToModel(entity));
    }

    public Payment entityToModel(PaymentEntity entity) {
        Payment resource = new Payment();
        if(Objects.isNull(entity)) {
            return resource;
        }
        BeanUtils.copyProperties(entity, resource);
        resource.add(Link.of("/api/v1/payments").withRel("payments"));
        resource.add(Link.of("/api/v1/payments/%s" + entity.getId()).withSelfRel());
        return resource;
    }

    public Payment getModel(Mono<Payment> m) {
        AtomicReference<Payment> model = new AtomicReference<>();
        m.cache().subscribe(model::set);
        return model.get();
    }

    public Flux<Payment> toListModel(Flux<PaymentEntity> entities) {
        if(Objects.isNull(entities)) {
            return Flux.empty();
        }
        return Flux.from(entities.map(this::entityToModel));
    }
}
