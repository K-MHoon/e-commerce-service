package com.kmhoon.app.hateoas;

import com.kmhoon.app.controllers.AddressController;
import com.kmhoon.app.entity.AddressEntity;
import com.kmhoon.app.model.Address;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AddressRepresentationModelAssembler extends RepresentationModelAssemblerSupport<AddressEntity, Address> {

    public AddressRepresentationModelAssembler() {
        super(AddressController.class, Address.class);
    }

    @Override
    public Address toModel(AddressEntity entity) {
        Address resource = createModelWithId(entity.getId(), entity);
        BeanUtils.copyProperties(entity, resource);
        resource.setId(entity.getId().toString());

        return resource.add(linkTo(methodOn(AddressController.class).getAddressesById(entity.getId().toString())).withSelfRel());
    }

    public List<Address> toListModel(Iterable<AddressEntity> entities) {
        if(Objects.isNull(entities)) {
            return List.of();
        }
        return StreamSupport.stream(entities.spliterator(), false).map(this::toModel).toList();
    }
}
