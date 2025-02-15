package com.kmhoon.app.entity;

import java.util.UUID;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("ecomm.address")
@Getter
@Setter
@Accessors(chain = true)
@ToString
@Builder
@NoArgsConstructor
public class AddressEntity {
    @Id
    @Column("id")
    private UUID id;

    @Column("number")
    private String number;

    @Column("residency")
    private String residency;

    @Column("street")
    private String street;

    @Column("city")
    private String city;

    @Column("state")
    private String state;

    @Column("country")
    private String country;

    @Column("pincode")
    private String pincode;
}