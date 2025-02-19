package com.kmhoon.app.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table("ecomm.product")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Builder
public class ProductEntity {

    @Id
    @Column("id")
    private UUID id;

    @NotNull(message = "Product name is required.")
    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("price")
    private BigDecimal price;

    @Column("count")

    private int count;

    @Column("image_url")
    private String imageUrl;

    @Transient
    private List<TagEntity> tags = new ArrayList<>();

    private ItemEntity item;

    public ProductEntity(UUID id, @NotNull(message = "Product name is required.") String name,
                         String description, BigDecimal price, int count, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.count = count;
        this.imageUrl = imageUrl;
    }
}