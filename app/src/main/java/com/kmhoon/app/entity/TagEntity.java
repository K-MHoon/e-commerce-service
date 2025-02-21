package com.kmhoon.app.entity;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("ecomm.tag")
@Getter
@Setter
@Accessors(chain = true)
@Builder
public class TagEntity {

    @Id
    @Column("id")
    private UUID id;

    @NotNull(message = "Product name is required.")
    @Column("name")
    private String name;
}