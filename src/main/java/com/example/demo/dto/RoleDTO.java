package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class RoleDTO {
    private Integer id;
    
    @NotBlank(message = "Role name cannot be blank")
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
