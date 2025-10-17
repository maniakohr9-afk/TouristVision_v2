package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "Favoriteplaces")
public class FavoritePlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFavorite")
    @JsonProperty("idFavorite")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    @JsonProperty("idUser")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "idPlace", referencedColumnName = "idPlace")
    @JsonProperty("idPlace")
    private TouristPlace place;
}