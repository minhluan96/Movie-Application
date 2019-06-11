package com.hcmus.movieapp.models;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Movie {

    @SerializedName(value = "id")
    private int id;

    @SerializedName(value = "name")
    private String name;

    @SerializedName(value = "imgURL")
    private String imgURL;

    private String status;

    @SerializedName(value = "score")
    private double score;

    @SerializedName(value = "description")
    private String description;

    @SerializedName(value = "genre")
    private String type;

    @SerializedName(value = "trailerURL")
    private String trailerURL;

    @SerializedName(value = "minAge")
    private int minAge;

    @SerializedName(value = "cast")
    private String casts;

    @SerializedName(value = "director")
    private String directors;

    @SerializedName(value = "initialRelease")
    private String releaseDate;

    @SerializedName(value = "runningTime")
    private String length;

    @SerializedName(value = "showTimes")
    private Showtime[] showtimes;

    public Movie(int id, String name, String imgURL, String status, double score, String description, int minAge) {
        this.id = id;
        this.name = name;
        this.imgURL = imgURL;
        this.status = status;
        this.score = score;
        this.description = description;
        this.minAge = minAge;
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

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public String minAgeDisplay() {
        return "C" + getMinAge();
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getTrailerURL() {
        return trailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }

    public String getCasts() {
        return casts;
    }

    public void setCasts(String casts) {
        this.casts = casts;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getReleaseDate() {
        TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");
        Calendar cal = Calendar.getInstance(tz);
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

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public int getLengthNumb() {
        String numbValue = length.substring(0, length.indexOf(" "));
        return Integer.valueOf(numbValue);
    }

    public Showtime[] getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(Showtime[] showtimes) {
        this.showtimes = showtimes;
    }
}
