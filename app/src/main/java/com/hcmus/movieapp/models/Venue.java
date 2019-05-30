package com.hcmus.movieapp.models;

import com.google.gson.annotations.SerializedName;

public class Venue {
    private int id;

    private String name;

    @SerializedName(value = "viewURL")
    private String viewUrl;

    private String address;

    @SerializedName(value = "totalSeats")
    private int capacity;

    private float avgPoint;

    private String introduce;

    public Venue(String name, String viewUrl, String address, int capacity, float avgPoint, String introduce) {
        this.name = name;
        this.viewUrl = viewUrl;
        this.address = address;
        this.capacity = capacity;
        this.avgPoint = avgPoint;
        this.introduce = introduce;
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

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String ViewUrl) {
        this.viewUrl = ViewUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public float getAvgPoint() {
        return avgPoint;
    }

    public void setAvgPoint(float avgPoint) {
        this.avgPoint = avgPoint;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
