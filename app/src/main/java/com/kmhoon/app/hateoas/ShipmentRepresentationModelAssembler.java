package com.kmhoon.app.hateoas;

import com.kmhoon.app.controllers.ShipmentController;
import com.kmhoon.app.entity.ProductEntity;
import com.kmhoon.app.entity.ShipmentEntity;
import com.kmhoon.app.model.Product;
import com.kmhoon.app.model.Shipment;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ShipmentRepresentationModelAssembler extends RepresentationModelAssemblerSupport<ShipmentEntity, Shipment> {

    public ShipmentRepresentationModelAssembler() {
        super(ShipmentController.class, Shipment.class);
    }

    @Override
    public Shipment toModel(ShipmentEntity entity) {
        Shipment resource = createModelWithId(entity.getId(), entity);
        BeanUtils.copyProperties(entity, resource);
        resource.setId(entity.getId().toString());

        return resource.add(linkTo(methodOn(ShipmentController.class).getShipmentByOrderId(entity.getId().toString())).withRel("byOrderId"));
    }

    public List<Shipment> toListModel(Iterable<ShipmentEntity> entities) {
        if (Objects.isNull(entities)) return List.of();
        return StreamSupport.stream(entities.spliterator(), false).map(this::toModel).toList();
    }
}
