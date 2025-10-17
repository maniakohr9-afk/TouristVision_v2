package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class TouristPlaceDTO {
    private Integer id;
    private Integer municipalityId;
    private Integer addressId;

    @NotBlank(message = "The place name cannot be blank")
    private String name;

    private String description;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getMunicipalityId() {
        return municipalityId;
    }
    public void setMunicipalityId(Integer municipalityId) {
        this.municipalityId = municipalityId;
    }
    public Integer getAddressId() {
        return addressId;
    }
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
