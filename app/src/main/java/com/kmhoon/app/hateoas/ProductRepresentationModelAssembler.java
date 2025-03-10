package com.kmhoon.app.hateoas;

import com.kmhoon.app.entity.ProductEntity;
import com.kmhoon.app.entity.TagEntity;
import com.kmhoon.app.model.Product;
import com.kmhoon.app.model.Tag;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.reactive.ReactiveRepresentationModelAssembler;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Component
public class ProductRepresentationModelAssembler implements ReactiveRepresentationModelAssembler<ProductEntity, Product>, HateoasSupport {

    private static String serverUri = null;

    private String getServerUri(@Nullable ServerWebExchange exchange) {
        if (Strings.isBlank(serverUri)) {
            serverUri = getUriComponentsBuilder(exchange).toUriString();
        }
        return serverUri;
    }

    @Override
    public Mono<Product> toModel(ProductEntity entity, ServerWebExchange exchange) {
        return Mono.just(entityToModel(entity, exchange));
    }

    public Product entityToModel(ProductEntity entity, ServerWebExchange exchange) {
        Product resource = new Product();
        if(Objects.isNull(entity)) {
            return resource;
        }
        BeanUtils.copyProperties(entity, resource);
        resource.setId(entity.getId().toString());
        resource.setTag(tagFromEntities(entity.getTags()));
        resource.add(Link.of("/api/v1/products").withRel("products"));
        resource.add(Link.of(String.format("/api/v1/products/%s", entity.getId())).withSelfRel());
        return resource;
    }

    public List<Tag> tagFromEntities(List<TagEntity> tags) {
        return tags.stream().map(t -> {
            Tag tag = new Tag();
            BeanUtils.copyProperties(t, tag);
            tag.setId(t.getId().toString());
            return tag;
        }).toList();
    }

    public Flux<Product> toListModel(Flux<ProductEntity> entities, ServerWebExchange exchange) {
        if (Objects.isNull(entities)) {
            return Flux.empty();
        }
        return Flux.from(entities.map(e -> entityToModel(e, exchange)));
    }
}
