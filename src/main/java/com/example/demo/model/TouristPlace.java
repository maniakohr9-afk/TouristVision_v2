package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "TouristPlaces")
public class TouristPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPlace")
    @JsonProperty("idPlace")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idMunicipality", referencedColumnName = "idMunicipality", nullable = false)
    @JsonProperty("municipality")
    private Municipality municipality;

    @ManyToOne
    @JoinColumn(name = "idAddress", referencedColumnName = "idAddress")
    @JsonProperty("address")
    private Address address;

    @Column(name = "name", nullable = false, length = 150)
    @JsonProperty("name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @JsonProperty("description")
    private String description;
}

