package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class MunicipalityDTO {
    private Integer id;
    private Integer stateId;
    
    @NotBlank(message = "Municipality name cannot be blank")
    private String name;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getStateId() {
        return stateId;
    }
    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
