package com.example.movieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Booking implements Parcelable {
    private int id;
    private String code;
    private String movieName;
    private String minAge;
    private String imgURL;
    private String cinemaName;
    private String iconURL;
    private String address;
    private String room;
    private String releaseDate;
    private String time;
    private String runningTime;
    private List<BookedSeat> bookedSeatList;
    private List<BookedCombo> bookedComboList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(code);
        dest.writeString(movieName);
        dest.writeString(minAge);
        dest.writeString(imgURL);
        dest.writeString(cinemaName);
        dest.writeString(iconURL);
        dest.writeString(address);
        dest.writeString(room);
        dest.writeString(releaseDate);
        dest.writeString(time);
        dest.writeString(runningTime);

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
        this.code = in.readString();
        this.movieName = in.readString();
        this.minAge = in.readString();
        this.imgURL = in.readString();
        this.cinemaName = in.readString();
        this.iconURL = in.readString();
        this.address = in.readString();
        this.room = in.readString();
        this.releaseDate = in.readString();
        this.time = in.readString();
        this.runningTime = in.readString();

        bookedSeatList = new ArrayList<>();
        in.readList(bookedSeatList, BookedSeat.class.getClassLoader());
        bookedComboList = new ArrayList<>();
        in.readList(bookedComboList, BookedCombo.class.getClassLoader());
    }

    public Booking() {}

    public Booking(int id, String code, String movieName, String minAge, String imgURL, String cinemaName, String iconURL, String address, String room, String releaseDate, String time, String runningTime, List<BookedSeat> bookedSeatList, List<BookedCombo> bookedComboList) {
        this.id = id;
        this.code = code;
        this.movieName = movieName;
        this.minAge = minAge;
        this.imgURL = imgURL;
        this.cinemaName = cinemaName;
        this.iconURL = iconURL;
        this.address = address;
        this.room = room;
        this.releaseDate = releaseDate;
        this.time = time;
        this.runningTime = runningTime;
        this.bookedSeatList = bookedSeatList;
        this.bookedComboList = bookedComboList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
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

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
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
}
