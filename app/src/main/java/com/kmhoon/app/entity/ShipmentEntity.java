package com.kmhoon.app.entity;

import java.sql.Timestamp;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("ecomm.shipment")
@Getter
@Setter
@Accessors(chain = true)
@Builder
public class ShipmentEntity {
    @Id
    @Column("id")
    private UUID id;

    @Column("est_delivery_date")
    private Timestamp estDeliveryDate;

    @Column("carrier")
    private String carrier;
}