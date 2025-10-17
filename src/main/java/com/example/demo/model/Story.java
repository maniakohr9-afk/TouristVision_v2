package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;



@Getter
@Setter
@Entity
@Table(name = "Stories")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idStory")
    @JsonProperty("idStory")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idPlace", referencedColumnName = "idPlace")
    @JsonProperty("idPlace")
    private TouristPlace place;

    @Column(name = "history", columnDefinition = "TEXT")
    @JsonProperty("history")
    private String history;

    @Column(name = "traditions", columnDefinition = "TEXT")
    @JsonProperty("traditions")
    private String traditions;
}