package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;



@Getter
@Setter
@Entity
@Table(name = "gastronomies")
public class Gastronomy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDish")
    @JsonProperty("idDish")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idPlace", referencedColumnName = "idPlace")
    @JsonProperty("idPlace")
    private TouristPlace place;

    @Column(name = "name", nullable = false, length = 100)
    @JsonProperty("name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @JsonProperty("description")
    private String description;
}