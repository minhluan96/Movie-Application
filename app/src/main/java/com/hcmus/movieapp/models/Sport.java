package com.hcmus.movieapp.models;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Sport {

    @SerializedName(value = "id")
    private int id;

    @SerializedName(value = "name")
    private String name;

    @SerializedName(value = "img_url")
    private String imgUrl;

    @SerializedName(value = "video_url")
    private String videoUrl;

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

    @SerializedName(value = "stands")
    private ShowMatch[] showmatchs;

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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
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

    public String getReleaseDateStr() {
        TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");
        java.util.Calendar cal = Calendar.getInstance(tz);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setCalendar(cal);
        try {
            cal.setTime(sdf.parse(releaseDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date = cal.getTime();
        sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
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

    public ShowMatch[] getShowmatchs() {
        return showmatchs;
    }

    public void setShowmatchs(ShowMatch[] showmatchs) {
        this.showmatchs = showmatchs;
    }
}
