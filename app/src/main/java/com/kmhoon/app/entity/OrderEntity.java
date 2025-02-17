package com.kmhoon.app.entity;

import com.kmhoon.app.model.Order.StatusEnum;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("ecomm.orders")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class OrderEntity {

    @Id
    @Column("id")
    private UUID id;

    @Column("customer_id")
    private UUID customerId;

    @Column("address_id")
    private UUID addressId;

    @Column("card_id")
    private UUID cardId;

    @Column("order_date")
    private Timestamp orderDate;

    @Column("total")
    private BigDecimal total;

    @Column("payment_id")
    private UUID paymentId;

    @Column("shipment_id")
    private UUID shipmentId;

    @Column("status")
    private StatusEnum status;

    private UUID cartId;

    private UserEntity userEntity;

    private AddressEntity addressEntity;

    private PaymentEntity paymentEntity;

    private List<ShipmentEntity> shipments = new ArrayList<>();

    private CardEntity cardEntity;

    private List<ItemEntity> items = new ArrayList<>();

    private AuthorizationEntity authorizationEntity;
}