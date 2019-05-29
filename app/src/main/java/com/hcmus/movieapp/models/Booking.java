package com.hcmus.movieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.hcmus.movieapp.utils.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Booking implements Parcelable {
    private int id;
    private int type;
    private String code;
    private String movieName;
    private String minAge;
    private String runningTime;
    private String movieType;
    private String genre;
    private String cinemaName;
    private String time;
    private String room;
    private String venue;
    private String address;
    private String imgURL;
    private String iconURL;
    private String releaseDate;
    private String eventName;
    private int eventCategory;
    private String unitName;
    private String gateway;
    private String organizer;
    private String description;
    private String block;

    private List<BookedSeat> bookedSeatList;
    private List<BookedCombo> bookedComboList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(type);
        dest.writeString(code);
        dest.writeString(movieName);
        dest.writeString(minAge);
        dest.writeString(runningTime);
        dest.writeString(movieType);
        dest.writeString(genre);
        dest.writeString(cinemaName);
        dest.writeString(time);
        dest.writeString(room);
        dest.writeString(venue);
        dest.writeString(address);
        dest.writeString(imgURL);
        dest.writeString(iconURL);
        dest.writeString(releaseDate);
        dest.writeString(eventName);
        dest.writeInt(eventCategory);
        dest.writeString(unitName);
        dest.writeString(gateway);
        dest.writeString(organizer);
        dest.writeString(description);
        dest.writeString(block);

        dest.writeList(bookedSeatList);
        dest.writeList(bookedComboList);
    }

    public static final Parcelable.Creator<Booking> CREATOR = new Parcelable.Creator<Booking>() {
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

    public Booking(Parcel in) {
        this.id = in.readInt();
        this.type = in.readInt();
        this.code = in.readString();
        this.movieName = in.readString();
        this.minAge = in.readString();
        this.runningTime = in.readString();
        this.movieType = in.readString();
        this.genre = in.readString();
        this.cinemaName = in.readString();
        this.time = in.readString();
        this.room = in.readString();
        this.venue = in.readString();
        this.address = in.readString();
        this.imgURL = in.readString();
        this.iconURL = in.readString();
        this.releaseDate = in.readString();
        this.eventName = in.readString();
        this.eventCategory = in.readInt();
        this.unitName = in.readString();
        this.gateway = in.readString();
        this.organizer = in.readString();
        this.description = in.readString();
        this.block = in.readString();

        bookedSeatList = new ArrayList<>();
        in.readList(bookedSeatList, BookedSeat.class.getClassLoader());
        bookedComboList = new ArrayList<>();
        in.readList(bookedComboList, BookedCombo.class.getClassLoader());
    }

    public Booking() {}

    public Booking(int type, String code, String movieName, String minAge, String runningTime, String movieType, String genre, String cinemaName, String time, String room, String venue, String address, String imgURL, String iconURL, String releaseDate, String eventName, int eventType, String unitName, String gateway, String organizer, String description, String block, List<BookedSeat> bookedSeatList, List<BookedCombo> bookedComboList) {
        this.type = type;
        this.code = code;
        this.movieName = movieName;
        this.minAge = minAge;
        this.runningTime = runningTime;
        this.movieType = movieType;
        this.genre = genre;
        this.cinemaName = cinemaName;
        this.time = time;
        this.room = room;
        this.venue = venue;
        this.address = address;
        this.imgURL = imgURL;
        this.iconURL = iconURL;
        this.releaseDate = releaseDate;
        this.eventName = eventName;
        this.eventCategory = eventType;
        this.unitName = unitName;
        this.gateway = gateway;
        this.organizer = organizer;
        this.description = description;
        this.block = block;

        this.bookedSeatList = bookedSeatList;
        this.bookedComboList = bookedComboList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(int eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public List<BookedSeat> getBookedSeatList() {
        return bookedSeatList;
    }

    public void setBookedSeatList(List<BookedSeat> bookedSeatList) {
        this.bookedSeatList = bookedSeatList;
    }

    public List<BookedCombo> getBookedComboList() {
        return bookedComboList;
    }

    public void setBookedComboList(List<BookedCombo> bookedComboList) {
        this.bookedComboList = bookedComboList;
    }

    public boolean isExpired() {
        SimpleDateFormat dateTimeFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currDateTime = new Date();
        Date releaseDateTimeFormatted = new Date();
        String releaseDateTime = Utilities.convertToSimpleDateFormat(this.releaseDate) + " " + this.getTime();
        try {
            releaseDateTimeFormatted = dateTimeFormat.parse(releaseDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (releaseDateTimeFormatted.before(currDateTime)) {
            return true;
        } else {
            return false;
        }
    }
}
