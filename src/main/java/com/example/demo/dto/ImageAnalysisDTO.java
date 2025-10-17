package com.example.demo.dto;

import java.math.BigDecimal;

public class ImageAnalysisDTO {
    private Integer id;
    private Integer imageId;
    private BigDecimal confidence;
    private String description;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getImageId() {
        return imageId;
    }
    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }
    public BigDecimal getConfidence() {
        return confidence;
    }
    public void setConfidence(BigDecimal confidence) {
        this.confidence = confidence;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
