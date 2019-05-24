package com.hcmus.movieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class BookedCombo implements Parcelable {
    private int bookingID;
    private String combo;
    private int quantity;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookingID);
        dest.writeString(combo);
        dest.writeInt(quantity);
    }

    public static final Parcelable.Creator<BookedCombo> CREATOR = new Parcelable.Creator<BookedCombo>() {
        public BookedCombo createFromParcel(Parcel in) {
            return new BookedCombo(in);
        }

        public BookedCombo[] newArray(int size) {
            return new BookedCombo[size];
        }
    };

    private BookedCombo(Parcel in) {
        bookingID = in.readInt();
        combo = in.readString();
        quantity = in.readInt();
    }

    public BookedCombo() {};

    public BookedCombo(int bookingID, String combo, int quantity) {
        this.bookingID = bookingID;
        this.combo = combo;
        this.quantity = quantity;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getCombo() {
        return combo;
    }

    public void setCombo(String combo) {
        this.combo = combo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
