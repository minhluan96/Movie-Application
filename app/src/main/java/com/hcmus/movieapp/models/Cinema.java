package com.hcmus.movieapp.models;

import com.google.gson.annotations.SerializedName;

public class Cinema {

    @SerializedName(value = "cinemaID")
    private int id;

    @SerializedName(value = "cinemaName")
    private String name;

    @SerializedName(value = "iconURL")
    private String imgUrl;

    @SerializedName(value = "address")
    private String address;

    private String description;

    private float avgPoint;

    @SerializedName(value = "showTimes")
    private Showtime[] showtime;

    public Cinema(int id, String name, String imgUrl, String address, String description, float avgPoint) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.address = address;
        this.description = description;
        this.avgPoint = avgPoint;
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

    public float getAvgPoint() {
        return avgPoint;
    }

    public void setAvgPoint(float avgPoint) {
        this.avgPoint = avgPoint;
    }

    public Showtime[] getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime[] showtime) {
        this.showtime = showtime;
    }
}
