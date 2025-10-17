package com.example.demo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "Municipalities")
public class Municipality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMunicipality")
    @JsonProperty("idMunicipality")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idState", 
    referencedColumnName = "idState", nullable = false)
    @JsonProperty("state")
    private State state;

    @Column(name = "name", nullable = false, length = 120)
    @JsonProperty("name")
    private String name;

    @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TouristPlace> touristPlaces;
}