package com.example.demo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "services")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idService")
    @JsonProperty("idService")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idPlace", referencedColumnName = "idPlace")
    @JsonProperty("idPlace")
    private TouristPlace place;

    @ManyToOne
    @JoinColumn(name = "idAddress", referencedColumnName = "idAddress")
    @JsonProperty("idAddress")
    private Address address;

    @Column(name = "name", nullable = false, length = 100)
    @JsonProperty("name")
    private String name;

    @Column(name = "type", nullable = false, length = 50)
    @JsonProperty("type")
    private String type;

    @Column(name = "description", columnDefinition = "TEXT")
    @JsonProperty("description")
    private String description;

    @Column(name = "priceRange", length = 50)
    @JsonProperty("priceRange")
    private String priceRange;

    @Column(name = "contactInfo", length = 150)
    @JsonProperty("contactInfo")
    private String contactInfo;
}
