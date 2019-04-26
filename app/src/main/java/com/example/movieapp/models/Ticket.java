package com.example.movieapp.models;

import android.icu.text.DecimalFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class Ticket {

    private int id;

    private String name;

    private double price;

    public Ticket(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getPriceString() {
        DecimalFormat formatter = new DecimalFormat("#,###.00");

        return formatter.format(price) + "đ";
    }
}