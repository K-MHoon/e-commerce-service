package com.kmhoon.app.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Table("ecomm.item")
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
@Builder
public class ItemEntity {

    @Id
    @Column("id")
    private UUID id;

    @Column("product_id")
    private UUID productId;

    @Column("unit_price")
    private BigDecimal price;

    @Column("quantity")
    private int quantity;
}