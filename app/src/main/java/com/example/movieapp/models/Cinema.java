package com.example.movieapp.models;

public class Cinema {

    private int id;

    private String name;

    private String imgUrl;

    private String address;

    private String description;

    public Cinema(int id, String name, String imgUrl, String address, String description) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
