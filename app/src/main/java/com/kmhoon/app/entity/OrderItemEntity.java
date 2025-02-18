package com.kmhoon.app.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("ecomm.order_item")
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
@Builder
public class OrderItemEntity {

    @Id
    @Column("id")
    private UUID id;

    @Column("order_id")
    private UUID orderId;

    @Column("item_id")
    private UUID itemId;
}