package com.kmhoon.app.entity;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("ecomm.user_address")
@Getter
@Setter
@Accessors(chain = true)
@Builder
public class UserAddressEntity {
    @Column("user_id")
    private UUID userId;

    @Column("address_id")
    private UUID addressID;
}