package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "textanalysis")
public class TextAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTextAnalysis")
    @JsonProperty("idTextAnalysis")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idReview", referencedColumnName = "idReview")
    @JsonProperty("idReview")
    private Review review;

    @Column(name = "sentiment", length = 20)
    @JsonProperty("sentiment")
    private String sentiment;

    @Column(name = "keyPhrases", columnDefinition = "TEXT")
    @JsonProperty("keyPhrases")
    private String keyPhrases;

    @Column(name = "language", length = 10)
    @JsonProperty("language")
    private String language;
}