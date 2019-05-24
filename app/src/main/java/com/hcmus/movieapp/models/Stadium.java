package com.hcmus.movieapp.models;

import com.google.gson.annotations.SerializedName;

public class Stadium {

    @SerializedName(value = "unitID")
    private int id;

    @SerializedName(value = "unitName")
    private String name;

    @SerializedName(value = "iconURL")
    private String iconURL;

    @SerializedName(value = "stands")
    private ShowMatch[] showMatches;

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

    public ShowMatch[] getShowMatches() {
        return showMatches;
    }

    public void setShowMatches(ShowMatch[] showMatches) {
        this.showMatches = showMatches;
    }
}
