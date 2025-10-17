package com.example.demo.model;


import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "Reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReview")
    @JsonProperty("idReview")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    @JsonProperty("idUser")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "idPlace", referencedColumnName = "idPlace")
    @JsonProperty("idPlace")
    private TouristPlace place;

    @Column(name = "title", nullable = false, length = 150)
    @JsonProperty("title")
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    @JsonProperty("content")
    private String content;

    @Column(name = "rating")
    @JsonProperty("rating")
    private Integer rating;
}