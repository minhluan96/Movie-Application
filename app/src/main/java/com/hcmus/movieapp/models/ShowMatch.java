package com.hcmus.movieapp.models;

import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowMatch {

    @SerializedName(value = "saleID")
    private int id;

    @SerializedName(value = "price")
    private long price;

    @SerializedName(value = "time")
    private String time;

    @SerializedName(value = "numberOfTickets")
    private int numberOfTicket;

    @SerializedName(value = "blockName")
    private String location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNumberOfTicket() {
        return numberOfTicket;
    }

    public void setNumberOfTicket(int numberOfTicket) {
        this.numberOfTicket = numberOfTicket;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFormatedPrice() {
        NumberFormat formatter = new DecimalFormat("#,### đ");
        String formattedNumber = formatter.format(price);
        return formattedNumber;
    }

    public String getTimeStart() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String timeStr = "";
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            timeStr = "00:00";
        }
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        timeStr = sdf.format(cal.getTime());
        return timeStr;
    }
}
