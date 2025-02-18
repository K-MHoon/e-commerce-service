package com.kmhoon.app.entity;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("ecomm.payment")
@Getter
@Setter
@Accessors(chain = true)
@Builder
public class PaymentEntity {

    @Id
    @Column("id")
    private UUID id;

    @Column("authorized")
    private boolean authorized;

    @Column("message")
    private String message;

    private OrderEntity orderEntity;
}