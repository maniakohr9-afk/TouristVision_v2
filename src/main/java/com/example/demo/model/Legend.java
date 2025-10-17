package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "Legends")
public class Legend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLegend")
    @JsonProperty("idLegend")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idPlace", referencedColumnName = "idPlace")
    @JsonProperty("idPlace")
    private TouristPlace place;

    @Column(name = "title", nullable = false, length = 150)
    @JsonProperty("title")
    private String title;

    @Column(name = "story", nullable = false, columnDefinition = "TEXT")
    @JsonProperty("story")
    private String story;

    @Column(name = "origin", length = 150)
    @JsonProperty("origin")
    private String origin;
}