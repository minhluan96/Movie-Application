package com.hcmus.movieapp.models;

import com.github.captain_miao.seatview.BaseSeatMo;

public class SeatMo extends BaseMo implements BaseSeatMo {


    public String seatName;
    /**
     * row Name
     */
    public String rowName;
    /**
     * row index
     */
    public int row;
    /**
     * column index
     */
    public int column;

    public int typeSeat;

    public double price;

    public int id;

    /**
     * seat status:1：available，0：sold，-1：unavailable
     */
    public int status;

    @Override
    public String getSeatName() {
        return seatName;
    }

    @Override
    public String getRowName() {
        return rowName;
    }

    @Override
    public boolean isOnSale() {
        return status == 1;
    }

    @Override
    public boolean isSold() {
        return status == 0;
    }

    @Override
    public boolean isSelected() {
        return status == 2;
    }

    @Override
    public boolean isSeatUnavailable() {
        return status == -1;
    }

    public void setSold() {
        status = 0;
    }
    public void setOnSale() {
        status = 1;
    }

    public void setSelected() {
        status = 2;
    }

    public int getTypeSeat() {
        return typeSeat;
    }

    public void setTypeSeat(int typeSeat) {
        this.typeSeat = typeSeat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
