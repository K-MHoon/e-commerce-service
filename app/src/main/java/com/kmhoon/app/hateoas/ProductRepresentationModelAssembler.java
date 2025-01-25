package com.kmhoon.app.hateoas;

import com.kmhoon.app.controllers.ProductController;
import com.kmhoon.app.entity.ProductEntity;
import com.kmhoon.app.entity.TagEntity;
import com.kmhoon.app.model.Product;
import com.kmhoon.app.model.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductRepresentationModelAssembler extends RepresentationModelAssemblerSupport<ProductEntity, Product> {

    public ProductRepresentationModelAssembler() {
        super(ProductController.class, Product.class);
    }

    @Override
    public Product toModel(ProductEntity entity) {
        String pid = entity.getId().toString();
        Product resource = createModelWithId(entity.getId(), entity);
        BeanUtils.copyProperties(entity, resource);
        resource.setId(pid);
        resource.setTag(entity.getTags().stream().map(this::TagEntityToTag).toList());
        resource.add(linkTo(methodOn(ProductController.class).getProduct(pid)).withSelfRel());
        resource.add(linkTo(methodOn(ProductController.class).queryProducts(null, null, 1, 10)).withRel("products"));

        return resource;
    }

    private Tag TagEntityToTag(TagEntity t) {
        return new Tag().id(t.getId().toString()).name(t.getName());
    }

    public List<Product> toListModel(Iterable<ProductEntity> entities) {
        if (Objects.isNull(entities)) {
            return List.of();
        }
        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .toList();
    }
}
