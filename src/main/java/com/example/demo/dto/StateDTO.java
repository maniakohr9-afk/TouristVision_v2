package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class StateDTO {
    private Integer id;
    
    @NotBlank(message = "State name cannot be blank")
    @Size(max = 100, message = "State name must not exceed 100 characters")
    private String name;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
