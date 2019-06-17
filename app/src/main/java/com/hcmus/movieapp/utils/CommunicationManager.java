package com.hcmus.movieapp.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.hcmus.movieapp.models.Account;
import com.hcmus.movieapp.models.Cinema;
import com.hcmus.movieapp.models.BookedCombo;
import com.hcmus.movieapp.models.BookedSeat;
import com.hcmus.movieapp.models.Booking;
import com.hcmus.movieapp.models.Cineplex;
import com.hcmus.movieapp.models.Movie;
import com.hcmus.movieapp.models.Notification;
import com.hcmus.movieapp.models.ShowMatch;
import com.hcmus.movieapp.models.Sport;
import com.hcmus.movieapp.models.Stadium;
import com.hcmus.movieapp.models.Ticket;
import com.hcmus.movieapp.models.TicketInfo;
import com.hcmus.movieapp.models.User;
import com.hcmus.movieapp.models.Venue;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.LinkedList;

public class CommunicationManager {

    private static final String TAG = CommunicationManager.class.getName();
    private static final String TAG_CLOUD = "CLOUD";

    private Context mContext;
    private RequestQueue mRequestQueue;

    public CommunicationManager() {
    }

    public CommunicationManager(Context mContext) {
        this.mContext = mContext;
        mRequestQueue = Volley.newRequestQueue(mContext);
    }

    public void cancelAllRequests() {
        mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }

    public void cancelAllRequestsAccept(final String tag) {
        mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return (!tag.equals(request.getTag()));
            }
        });
    }

    public void cancelRequest(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            mRequestQueue.cancelAll(tag);
        } else {
            mRequestQueue.cancelAll(TAG);
        }
    }

    private <T> void addToRequestQueue(String tag, Request<T> request) {
        if (!TextUtils.isEmpty(tag)) {
            request.setTag(tag);
        } else {
            request.setTag(TAG);
        }
        mRequestQueue.add(request);
    }

    public void getLatestMovies(String tag, final DataParser.DataResponseListener<LinkedList<Movie>> listener) {
        Type type = new TypeToken<LinkedList<Movie>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Movie", "Latest", null, new DataParser<>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getHotEvents(String tag, final DataParser.DataResponseListener<LinkedList<Sport>> listener) {
        Type type = new TypeToken<LinkedList<Sport>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Event", "HotEvent", null, new DataParser<>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getUpcomingEvents(String tag, final DataParser.DataResponseListener<LinkedList<Sport>> listener) {
        Type type = new TypeToken<LinkedList<Sport>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Event", "Upcoming", null, new DataParser<>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getEventDetails(String tag, int eventID, final DataParser.DataResponseListener<Sport> listener) {
        Type type = new TypeToken<LinkedList<Sport>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Event", "Detail", "/" + eventID, null, new DataParser<>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getUserInfo(String tag, String accountID, final DataParser.DataResponseListener<LinkedList<User>> listener) {
        Type type = new TypeToken<LinkedList<User>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "User", "Info", accountID,null, new DataParser<LinkedList<User>>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getBookedSeatByMovie(String tag, int movieId, int cinemaId, final DataParser.DataResponseListener<LinkedList<BookedSeat>> listener) {
        Type type = new TypeToken<LinkedList<BookedSeat>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Seat", "Booking", movieId + "/location/" + cinemaId,null, new DataParser<>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getBookedSeatByEvent(String tag, int saleID, int locationID, final DataParser.DataResponseListener<LinkedList<BookedSeat>> listener) {
        Type type = new TypeToken<LinkedList<BookedSeat>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Seat", "BookingEvent", saleID + "/location/" + locationID,null, new DataParser<>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getBookingsByAccount(String tag, String accountID, final DataParser.DataResponseListener<LinkedList<Booking>> listener) {
        Type type = new TypeToken<LinkedList<Booking>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Account", "PurchasedTickets", accountID,null, new DataParser<LinkedList<Booking>>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getBookedSeatsByBooking(String tag, String bookingID, final DataParser.DataResponseListener<LinkedList<BookedSeat>> listener) {
        Type type = new TypeToken<LinkedList<BookedSeat>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Booking", "BookedSeats", bookingID,null, new DataParser<LinkedList<BookedSeat>>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getBookedCombosByBooking(String tag, String bookingID, final DataParser.DataResponseListener<LinkedList<BookedCombo>> listener) {
        Type type = new TypeToken<LinkedList<BookedCombo>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Booking", "BookedCombos", bookingID,null, new DataParser<LinkedList<BookedCombo>>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getAllNotifications(String tag, final DataParser.DataResponseListener<LinkedList<Notification>> listener) {
        Type type = new TypeToken<LinkedList<Notification>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Notifications", "GetAll", null, new DataParser<LinkedList<Notification>>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getTicketInfoOfUser(String tag, int ticketId, int userId, final DataParser.DataResponseListener<TicketInfo> listener) {
        Type type = new TypeToken<TicketInfo>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Ticket", "Info", ticketId + "/account/" + userId,null, new DataParser<TicketInfo>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getAllCineplex(String tag, final DataParser.DataResponseListener<LinkedList<Cineplex>> listener) {
        Type type = new TypeToken<LinkedList<Cineplex>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Location", "Cineplex", null, new DataParser<LinkedList<Cineplex>>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getCinemaShowtimes(String tag, int cinema_id, String date, final DataParser.DataResponseListener<LinkedList<Movie>> listener) {
        Type type = new TypeToken<LinkedList<Movie>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Cinema", "Showtimes", cinema_id + "/showing/" + date, null, new DataParser<>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getAllVenues(String tag, final DataParser.DataResponseListener<LinkedList<Venue>> listener) {
        Type type = new TypeToken<LinkedList<Venue>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Location", "Venues", null, new DataParser<LinkedList<Venue>>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getStadiumShowmatchs(String tag, int venue_id, String date, final DataParser.DataResponseListener<LinkedList<Sport>> listener) {
        Type type = new TypeToken<LinkedList<Sport>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Stadium", "Showmatchs", venue_id + "/sale/" + date, null, new DataParser<>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void doLogin(String tag, JSONObject body, DataParser.DataResponseListener<LinkedList<Account>> listener) {
        Type type = new TypeToken<LinkedList<Account>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.POST, "Account", "Login", body, new DataParser(listener,null, type));
    }

    public void doRegister(String tag, JSONObject body, DataParser.DataResponseListener<LinkedList<Account>> listener) {
        Type type = new TypeToken<LinkedList<Account>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.POST, "Account", "Register", body, new DataParser(listener,null, type));
    }

    public void doChangePassword(String tag, JSONObject body, DataParser.DataResponseListener<JSONObject> listener) {
        makeJSONObjectRequest(tag, Request.Method.POST, "Account", "ChangePassword", body, new DataParser(listener,null, JSONObject.class));
    }

    public void doUpdateUserInfo(String tag, JSONObject body, DataParser.DataResponseListener<JSONObject> listener) {
        makeJSONObjectRequest(tag, Request.Method.POST, "User", "Update", body, new DataParser(listener, null, JSONObject.class));
    }

    public void createMovieTicket(String tag, JSONObject body, DataParser.DataResponseListener<Ticket> listener) {
        makeJSONObjectRequest(tag, Request.Method.POST, "Ticket", "Create", body, new DataParser(listener, null, Ticket.class));
    }

    public void getMovieCalendars(String tag, int movie_id, String date, final DataParser.DataResponseListener<LinkedList<Cinema>> listener) {
        Type type = new TypeToken<LinkedList<Cinema>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "MovieDetail", "Calendar", "/" + movie_id + "/showing/" + date, null, new DataParser<>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getEventCalendars(String tag, int eventId, String date, final DataParser.DataResponseListener<LinkedList<Stadium>> listener) {
        Type type = new TypeToken<LinkedList<Stadium>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "EventDetail", "Calendar", "/" + eventId + "/sale/" + date, null, new DataParser<>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getNowShowingMovies(String tag, int size, int page, final DataParser.DataResponseListener<LinkedList<Movie>> listener) {
        Type type = new TypeToken<LinkedList<Movie>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Movie", "NowShowing", "?pageNo=" + page + "&size=" + size,null, new DataParser<LinkedList<Movie>>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getUpcomingMovies(String tag, int size, int page, final DataParser.DataResponseListener<LinkedList<Movie>> listener) {
        Type type = new TypeToken<LinkedList<Movie>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Movie", "Upcoming", "?pageNo=" + page + "&size=" + size, null, new DataParser<LinkedList<Movie>>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getMovieDetail(String tag, String movieID, final DataParser.DataResponseListener<Movie> listener) {
        Type type = new TypeToken<Movie>() {
        }.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Movie", "Detail", movieID,null, new DataParser<Movie>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    // TODO: rename this function later
    public void getMovieByPage(String tag, int page, final DataParser.DataResponseListener<LinkedList<Movie>> listener) {
        Type type = new TypeToken<LinkedList<Movie>>() {
        }.getType();
        int perPage = 50;
        makeJSONObjectRequest(tag, Request.Method.GET, "Movie", "List", page + "/" + perPage,null, new DataParser<LinkedList<Movie>>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void makeJSONObjectRequest(String tag, int method, final String service, final String entry, final JSONObject params, final DataParser parser) {
        makeJSONObjectRequest(tag, method, service, entry, "", params, parser);
    }

    public void makeJSONObjectRequest(String tag, int method, String service, String entry, String urlParams, Object params, DataParser parser) {
        Requester requester = new Requester(service, entry, method, params, response -> {
            Log.d(TAG_CLOUD, "response: " + response);
            parser.parse(response);
        }, error -> parser.notifyError(error));
        if (!TextUtils.isEmpty(urlParams)) {
            requester.setTrailingUrl(urlParams);
        }
        addToRequestQueue(tag, requester);
    }
}
