package com.kmhoon.app.entity;

import com.kmhoon.app.model.Order.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class OrderEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "TOTAL")
    private BigDecimal total;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="CUSTOMER_ID", nullable=false)
    private UserEntity userEntity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID", insertable=false, updatable=false)
    private AddressEntity addressEntity;

    @OneToOne(cascade = CascadeType.ALL )
    @JoinColumn(name = "PAYMENT_ID", referencedColumnName = "ID")
    private PaymentEntity paymentEntity;

    @JoinColumn(name = "SHIPMENT_ID", referencedColumnName = "ID")
    @OneToOne
    private ShipmentEntity shipment;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "CARD_ID", referencedColumnName = "ID")
    private CardEntity cardEntity;

    @Column(name = "ORDER_DATE")
    private Timestamp orderDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "ORDER_ITEM",
            joinColumns = @JoinColumn(name = "ORDER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
    )
    @Builder.Default
    private List<ItemEntity> items = new ArrayList<>();

    @OneToOne(mappedBy = "orderEntity")
    private AuthorizationEntity authorizationEntity;
}
