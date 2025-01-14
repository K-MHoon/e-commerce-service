package com.kmhoon.app.entity;

import com.kmhoon.app.model.Item;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ItemEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
    private ProductEntity product;

    @Column(name = "UNIT_PRICE")
    private BigDecimal price;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @ManyToMany(mappedBy = "items", fetch = FetchType.LAZY)
    @Builder.Default
    private List<CartEntity> cart = new ArrayList<>();

    @ManyToMany(mappedBy = "items", fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrderEntity> orders = new ArrayList<>();

    public void updateQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void updatePrice(BigDecimal unitPrice) {
        this.price = unitPrice;
    }
}
