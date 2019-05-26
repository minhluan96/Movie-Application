package com.hcmus.movieapp.models;

import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Showtime {

    @SerializedName(value = "id")
    private int id;

    @SerializedName(value = "location_name")
    private String branchName;

    @SerializedName(value = "time")
    private String timeStart;

    private String timeEnd;

    @SerializedName(value = "type")
    private String typeMovie;

    @SerializedName(value = "price")
    private long price;

    @SerializedName(value = "location_id")
    private int location_id;

    private int totalSeats;

    public Showtime(String branchName, String timeStart, String timeEnd, String typeMovie, long price, int totalSeats) {
        this.branchName = branchName;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.typeMovie = typeMovie;
        this.price = price;
        this.totalSeats = totalSeats;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getTimeStart() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = "";
        Date date = null;
        try {
            date = sdf.parse(timeStart);
        } catch (ParseException e) {
            e.printStackTrace();
            time = "00:00";
        }
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        time = sdf.format(cal.getTime());
        return time;
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

    public long getPrice() {
        return price;
    }

    public String getFormatedPrice() {
        NumberFormat formatter = new DecimalFormat("#,### đ");
        String formattedNumber = formatter.format(price);
        return formattedNumber;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFinishTime(int length) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d = null;
        try {
            d = df.parse(timeStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, length);
        String newTime = df.format(cal.getTime());
        return newTime;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }
}
