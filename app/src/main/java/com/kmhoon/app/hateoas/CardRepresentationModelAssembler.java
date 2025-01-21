package com.kmhoon.app.hateoas;

import com.kmhoon.app.controllers.CardController;
import com.kmhoon.app.entity.CardEntity;
import com.kmhoon.app.model.Card;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CardRepresentationModelAssembler extends RepresentationModelAssemblerSupport<CardEntity, Card> {

    public CardRepresentationModelAssembler() {
        super(CardController.class, Card.class);
    }

    @Override
    public Card toModel(CardEntity entity) {
        String uid = Objects.isNull(entity.getUser()) ? null : entity.getUser().getId().toString();
        Card resource = createModelWithId(entity.getId(), entity);
        BeanUtils.copyProperties(entity, resource);
        resource.id(entity.getId().toString())
                .cardNumber(entity.getNumber())
                .cvv(entity.getCvv())
                .expires(entity.getExpires())
                .userId(uid);

        return resource.add(linkTo(methodOn(CardController.class).getCardById(entity.getId().toString())).withSelfRel());
    }

    public List<Card> toListModel(Iterable<CardEntity> entities) {
        if(Objects.isNull(entities)) {
            return List.of();
        }
        return StreamSupport.stream(entities.spliterator(), false).map(this::toModel).toList();
    }
}
