
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

@Table("ecomm.authorization")
@Getter
@Setter
@Accessors(chain = true)
@Builder
public class AuthorizationEntity {

    @Id
    @Column("id")
    private UUID id;

    @Column("authorized")
    private boolean authorized;

    @Column("time")
    private Timestamp time;

    @Column("message")
    private String message;

    @Column("error")
    private String error;

    private OrderEntity orderEntity;
}