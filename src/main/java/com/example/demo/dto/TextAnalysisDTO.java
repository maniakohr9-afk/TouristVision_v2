package com.example.demo.dto;

public class TextAnalysisDTO {
    private Integer id;
    private Integer reviewId;
    private String sentiment;
    private String keyPhrases;
    private String language;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getReviewId() {
        return reviewId;
    }
    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }
    public String getSentiment() {
        return sentiment;
    }
    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }
    public String getKeyPhrases() {
        return keyPhrases;
    }
    public void setKeyPhrases(String keyPhrases) {
        this.keyPhrases = keyPhrases;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
}
