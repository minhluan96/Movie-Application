package com.hcmus.movieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class BookedSeat implements Parcelable {
    private int bookingID;
    private String block;
    private String row;
    private String number;
    private int type;
    private int status;
    private int id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookingID);
        dest.writeString(block);
        dest.writeString(row);
        dest.writeString(number);
        dest.writeInt(id);
        dest.writeInt(status);
        dest.writeInt(type);
    }

    public static final Parcelable.Creator<BookedSeat> CREATOR = new Parcelable.Creator<BookedSeat>() {
        public BookedSeat createFromParcel(Parcel in) {
            return new BookedSeat(in);
        }

        public BookedSeat[] newArray(int size) {
            return new BookedSeat[size];
        }
    };

    private BookedSeat(Parcel in) {
        bookingID = in.readInt();
        block = in.readString();
        row =  in.readString();
        number = in.readString();
        type = in.readInt();
        status = in.readInt();
        id = in.readInt();
    }

    public BookedSeat() {};

    public BookedSeat(int bookingID, String block, String row, String number, int type, int status) {
        this.bookingID = bookingID;
        this.block = block;
        this.row = row;
        this.number = number;
        this.type = type;
        this.status = status;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
