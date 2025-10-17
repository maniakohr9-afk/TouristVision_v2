package com.example.demo.dto;

public class StoryDTO {
    private Integer id;
    private Integer placeId;
    private String history;
    private String traditions;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getPlaceId() {
        return placeId;
    }
    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }
    public String getHistory() {
        return history;
    }
    public void setHistory(String history) {
        this.history = history;
    }
    public String getTraditions() {
        return traditions;
    }
    public void setTraditions(String traditions) {
        this.traditions = traditions;
    }
}

