package com.kmhoon.app.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("ecomm.card")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class CardEntity {
    @Id
    @Column("id")
    private UUID id;

    @Column("number")
    private String number;

    @Column("expires")
    private String expires;

    @Column("cvv")
    private String cvv;

    @Column("user_id")
    private UUID userId;

    private UserEntity user;

    private OrderEntity orderEntity;
}