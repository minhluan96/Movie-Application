package com.hcmus.movieapp.models;

import java.util.Date;

public class Calendar {

    private String name;

    private String numberOfDate;

    private boolean isToday;

    private Date date;

    public Calendar(String name, String numberOfDate, boolean isToday, Date date) {
        this.name = name;
        this.numberOfDate = numberOfDate;
        this.isToday = isToday;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
