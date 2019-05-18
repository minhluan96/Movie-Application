package com.example.movieapp.models;

import com.google.gson.annotations.SerializedName;

public class Sport {

    @SerializedName(value = "id")
    private int id;

    @SerializedName(value = "name")
    private String name;

    @SerializedName(value = "img_url")
    private String imgUrl;

    @SerializedName(value = "description")
    private String description;

    @SerializedName(value = "releaseDate")
    private String releaseDate;

    @SerializedName(value = "time")
    private String time;

    @SerializedName(value = "organizer")
    private String host;

    @SerializedName(value = "category")
    private int category;

    @SerializedName(value = "number_of_tickets")
    private int numberOfTickets;

    public Sport(int id, String name, String imgUrl, String status, String description) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }
}
