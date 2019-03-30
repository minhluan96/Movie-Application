package com.example.movieapp.models;

public class Showtime {

    private String branchName;

    private String timeStart;

    private String timeEnd;

    private String typeMovie;

    private String price;

    private String distance;

    public Showtime(String branchName, String timeStart, String timeEnd, String typeMovie, String price, String distance) {
        this.branchName = branchName;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.typeMovie = typeMovie;
        this.price = price;
        this.distance = distance;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTypeMovie() {
        return typeMovie;
    }

    public void setTypeMovie(String typeMovie) {
        this.typeMovie = typeMovie;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
