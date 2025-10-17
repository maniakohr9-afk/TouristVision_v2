package com.example.demo.model;


import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "ReviewImages")
public class ReviewImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idImage")
    @JsonProperty("idImage")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idReview", referencedColumnName = "idReview")
    @JsonProperty("idReview")
    private Review review;

    @Column(name = "url", nullable = false, columnDefinition = "TEXT")
    @JsonProperty("url")
    private String url;

    @Column(name = "description", length = 200)
    @JsonProperty("description")
    private String description;
}