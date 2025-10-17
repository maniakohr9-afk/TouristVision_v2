package com.example.demo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "Addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAddress")
    @JsonProperty("idAddress")
    private Integer id;

    @Column(name = "street")
    @JsonProperty("street")
    private String street;

    @Column(name = "neighborhood")
    @JsonProperty("neighborhood")
    private String neighborhood;

    @Column(name = "postalCode")
    @JsonProperty("postalCode")
    private String postalCode;

    @Column(name = "latitude")
    @JsonProperty("latitude")
    private Double latitude;

    @Column(name = "longitude")
    @JsonProperty("longitude")
    private Double longitude;

    @Column(name = "reference", columnDefinition = "TEXT")
    @JsonProperty("reference")
    private String reference;
}