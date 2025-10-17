package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEvent")
    @JsonProperty("idEvent")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idPlace", referencedColumnName = "idPlace")
    @JsonProperty("idPlace")
    private TouristPlace place;

    @Column(name = "name", nullable = false, length = 150)
    @JsonProperty("name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    @JsonProperty("description")
    private String description;

    @Column(name = "eventDate")
    @JsonProperty("eventDate")
    private String eventDate;
}