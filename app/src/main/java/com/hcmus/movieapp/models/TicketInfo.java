package com.hcmus.movieapp.models;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class TicketInfo {

    @SerializedName(value = "id")
    private int id;

    @SerializedName(value = "movieName")
    private String movieName;

    @SerializedName(value = "minAge")
    private String minAge;

    @SerializedName(value = "type")
    private String type;

    @SerializedName(value = "genre")
    private String genre;

    @SerializedName(value = "runningTime")
    private String runningTime;

    @SerializedName(value = "cinemaName")
    private String cinemaName;

    @SerializedName(value = "iconURL")
    private String iconURL;

    @SerializedName(value = "address")
    private String address;

    @SerializedName(value = "room")
    private String room;

    @SerializedName(value = "releaseDate")
    private String releaseDate;

    @SerializedName(value = "time")
    private String time;

    @SerializedName(value = "code")
    private String code;

    @SerializedName(value = "venue")
    private String location;

    @SerializedName(value = "imgURL")
    private String imgURL;

    @SerializedName(value = "eventName")
    private String eventName;

    @SerializedName(value = "eventCategory")
    private int eventCategory;

    @SerializedName(value = "unitName")
    private String unitName;

    @SerializedName(value = "gateway")
    private String gateway;

    @SerializedName(value = "organizer")
    private String host;

    @SerializedName(value = "description")
    private String description;

    @SerializedName(value = "block")
    private String block;

    @SerializedName(value = "bookedSeats")
    private List<BookedSeat> bookedSeats;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMinAge() {
        return minAge;
    }

    public void setMinAge(String minAge) {
        this.minAge = minAge;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<BookedSeat> getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(List<BookedSeat> bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public String getReleaseDateAsString() {
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
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    public String getTimeStr() {
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

    public String getLocation() {
        return location;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getEventName() {
        return eventName;
    }

    public int getEventCategory() {
        return eventCategory;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getGateway() {
        return gateway;
    }

    public String getHost() {
        return host;
    }

    public String getDescription() {
        return description;
    }

    public String getBlock() {
        return block;
    }
}
