package com.example.demo.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "ImageAnalysis")
public class ImageAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idImageAnalysis")
    @JsonProperty("idImageAnalysis")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idImage", referencedColumnName = "idImage")
    @JsonProperty("idImage")
    private ReviewImage image;

    @Column(name = "confidence", precision = 4, scale = 3)
    @JsonProperty("confidence")
    private BigDecimal confidence;

    @Column(name = "description", columnDefinition = "TEXT")
    @JsonProperty("description")
    private String description;
}