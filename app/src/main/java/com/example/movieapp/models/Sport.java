package com.example.movieapp.models;

public class Sport {

    private int id;

    private String name;

    private String imgUrl;

    private String status;

    private String description;

    public Sport(int id, String name, String imgUrl, String status, String description) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.status = status;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
