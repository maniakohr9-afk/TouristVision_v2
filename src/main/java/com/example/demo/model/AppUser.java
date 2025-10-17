package com.example.demo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "Appusers")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    @JsonProperty("idUser")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idRole", referencedColumnName = "idRole", nullable = false)
    @JsonProperty("role")
    private Rol role;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    @JsonProperty("username")
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    @JsonProperty("password")
    private String password;
}
