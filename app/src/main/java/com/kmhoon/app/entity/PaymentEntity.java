package com.kmhoon.app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PaymentEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @Column(name="AUTHORIZED")
    private boolean authorized;

    @Column(name = "MESSAGE")
    private String message;

    @OneToOne(mappedBy = "paymentEntity")
    private OrderEntity orderEntity;
}
