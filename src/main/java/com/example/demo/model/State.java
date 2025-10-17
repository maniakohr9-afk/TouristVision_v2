package com.example.demo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


import java.util.List;



@Getter
@Setter
@Entity
@Table(name = "States")
@Data
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idState")
    @JsonProperty("idState")
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    @JsonProperty("name")
    private String name;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Municipality> municipalities;
}
