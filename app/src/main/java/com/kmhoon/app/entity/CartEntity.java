package com.kmhoon.app.entity;

import java.util.*;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("ecomm.cart")
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Builder
public class CartEntity {

    @Id
    @Column("id")
    private UUID id;

    private UserEntity user;

    private List<ItemEntity> items = new ArrayList<>();
}