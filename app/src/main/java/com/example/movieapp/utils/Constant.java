package com.example.movieapp.utils;

public class Constant {
    public static final String BASE_URL = "https://ticketnow-api.herokuapp.com";

    public static final String LOGGED_IN_PREF = "logged_in_status";

    public static final String LAST_SELECTED_TAB_PREF = "last_selected_tab";

    public interface SeatType {
        int NORMAL = 1;
        int VIP = 2;
        int COUPLE = 3;
    }

    public interface SeatTypePosition {
        int NORMAL = 3;
        int VIP = 8;
        int COUPLE = 10;
    }
}
