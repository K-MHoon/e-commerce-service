package com.kmhoon.app.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("ecomm.user")
@Getter
@Builder
@Setter
@Accessors(chain = true)
public class UserEntity {

    @Id
    @Column("id")
    private UUID id;

    @NotNull(message = "User name is required.")
    @Column("username")
    private String username;

    @Column("password")
    private String password;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("email")
    private String email;

    @Column("phone")
    private String phone;

    @Column("user_status")
    private String userStatus;

    private CardEntity card;

    private CartEntity cart;
}