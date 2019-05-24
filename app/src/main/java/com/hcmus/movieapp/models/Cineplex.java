package com.hcmus.movieapp.models;

public class Cineplex {
    private int id;
    private String name;
    private String iconURL;
    private Cinema[] cinemas;

    public Cineplex(String name, String iconURL, Cinema[] cinemas) {
        this.name = name;
        this.iconURL = iconURL;
        this.cinemas = cinemas;
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

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public Cinema[] getCinemas() {
        return cinemas;
    }

    public void setCinemas(Cinema[] cinemas) {
        this.cinemas = cinemas;
    }
}
