package com.hcmus.movieapp.models;

public class Venue {
    private int id;
    private String name;
    private String viewURL;
    private String address;
    private float avgPoint;

    public Venue(String name, String viewURL, String address, float avgPoint) {
        this.name = name;
        this.viewURL = viewURL;
        this.address = address;
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

    public String getViewURL() {
        return viewURL;
    }

    public void setViewURL(String viewURL) {
        this.viewURL = viewURL;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getAvgPoint() {
        return avgPoint;
    }

    public void setAvgPoint(float avgPoint) {
        this.avgPoint = avgPoint;
    }
}
