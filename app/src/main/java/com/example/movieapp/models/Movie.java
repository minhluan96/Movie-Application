package com.example.movieapp.models;

public class Movie {

    private int id;

    private String name;

    private String imgUrl;

    private String status;

    private double score;

    private String description;

    private int minAge;

    public Movie(int id, String name, String imgUrl, String status, double score, String description, int minAge) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.status = status;
        this.score = score;
        this.description = description;
        this.minAge = minAge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public String minAgeDisplay() {
        return "C" + getMinAge();
    }
}
