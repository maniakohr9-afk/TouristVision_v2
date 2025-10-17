package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "Roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRole")
    @JsonProperty("idRole")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    @JsonProperty("name")
    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppUser> users;
}
