package com.kmhoon.app.hateoas;

import com.kmhoon.app.controllers.PaymentController;
import com.kmhoon.app.entity.PaymentEntity;
import com.kmhoon.app.model.Payment;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PaymentRepresentationModelAssembler extends RepresentationModelAssemblerSupport<PaymentEntity, Payment> {

    public PaymentRepresentationModelAssembler() {
        super(PaymentController.class, Payment.class);
    }

    @Override
    public Payment toModel(PaymentEntity entity) {
        Payment resource = createModelWithId(entity.getId(), entity);
        BeanUtils.copyProperties(entity, resource);
        resource.setId(entity.getId().toString());

        return resource.add(linkTo(methodOn(PaymentController.class).getOrdersPaymentAuthorization(entity.getId().toString())).withSelfRel());
    }

    public List<Payment> toListModel(Iterable<PaymentEntity> entities) {
        if(Objects.isNull(entities)) {
            return List.of();
        }
        return StreamSupport.stream(entities.spliterator(), false).map(this::toModel).toList();
    }
}
