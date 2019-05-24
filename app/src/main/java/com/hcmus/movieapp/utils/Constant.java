package com.hcmus.movieapp.utils;

import java.util.HashMap;
import java.util.Map;

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

    public interface EventType {
        int MOVIE = 1;
        int SPORT = 2;
    }

    public interface TicketType {
        int MOVIE = 0;
        int EVENT = 1;
    }

    public interface SizeThumbnail {
        int SMALL = 1;
        int MEDIUM = 2;
        int LARGEST = 3;
    }
}
