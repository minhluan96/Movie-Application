package com.example.movieapp.models;

public class Calendar {

    private String name;

    private String numberOfDate;

    private boolean isToday;

    public Calendar(String name, String numberOfDate, boolean isToday) {
        this.name = name;
        this.numberOfDate = numberOfDate;
        this.isToday = isToday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberOfDate() {
        return numberOfDate;
    }

    public void setNumberOfDate(String numberOfDate) {
        this.numberOfDate = numberOfDate;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }
}
