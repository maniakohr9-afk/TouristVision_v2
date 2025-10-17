package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;    


@Getter
@Setter
@Entity
@Table(name = "servicereviews")
public class ServiceReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idServiceReview")
    @JsonProperty("idServiceReview")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idService", referencedColumnName = "idService")
    @JsonProperty("idService")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    @JsonProperty("idUser")
    private AppUser user;

    @Column(name = "rating")
    @JsonProperty("rating")
    private Integer rating;

    @Column(name = "comment", columnDefinition = "TEXT")
    @JsonProperty("comment")
    private String comment;
}