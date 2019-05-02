package com.example.movieapp.fragments;

import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.movieapp.R;
import com.example.movieapp.adapters.ExpandableListAdapter;
import com.example.movieapp.adapters.HorizontalCalendarAdapter;
import com.example.movieapp.models.Calendar;
import com.example.movieapp.models.Cinema;
import com.example.movieapp.models.Movie;
import com.example.movieapp.models.Showtime;
import com.example.movieapp.utils.AppManager;
import com.example.movieapp.utils.DataParser;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CalendarFragment extends Fragment implements HorizontalCalendarAdapter.ItemCalendarListener {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private HorizontalCalendarAdapter horizontalCalendarAdapter;
    private TextView txtNotify;
    private RecyclerView rvCalendar;
    private RecyclerView.LayoutManager rvCalendarLayoutManager;
    private List<Cinema> cinemas;
    private List<Calendar> calendars;
    private HashMap<Cinema, List<Showtime>> listDataChild;
    private Movie movie;
    private static final String TAG_MOVIE_CALENDARS = "TAG_MOVIE_CALENDARS";
    private Calendar calendar;

    public CalendarFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calendar_fragment, container, false);

        rvCalendar = v.findViewById(R.id.rv_calendar);
        expandableListView = v.findViewById(R.id.list_expand);
        txtNotify = v.findViewById(R.id.txtNotify);


        setupDummyData();
        expandableListAdapter = new ExpandableListAdapter(getContext(), cinemas, listDataChild);
        expandableListAdapter.setMovie(movie);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListAdapter.setCalendar(calendar);

        rvCalendarLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvCalendar.setLayoutManager(rvCalendarLayoutManager);
        horizontalCalendarAdapter = new HorizontalCalendarAdapter(calendars, getContext());
        horizontalCalendarAdapter.setListener(this);
        rvCalendar.setAdapter(horizontalCalendarAdapter);

        Display display =  getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                50, r.getDisplayMetrics());
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expandableListView.setIndicatorBounds(width - px, width);
        } else {
            expandableListView.setIndicatorBoundsRelative(width - px, width);
        }
        return v;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    private void setupDummyData() {
        cinemas = new ArrayList<>();
        listDataChild = new HashMap<>();

        initCalendarTime();
        getMovieCalendars();
    }

    private void initCalendarTime() {
        Map<String, String> dayDictionary = new HashMap<>();
        dayDictionary.put("Monday", "Thứ hai");
        dayDictionary.put("Tuesday", "Thứ ba");
        dayDictionary.put("Wednesday", "Thứ tư");
        dayDictionary.put("Thursday", "Thứ năm");
        dayDictionary.put("Friday", "Thứ sáu");
        dayDictionary.put("Saturday", "Thứ bảy");
        dayDictionary.put("Sunday", "Chủ nhật");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(new Date());
        calendars = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            c.add(java.util.Calendar.DATE, i == 0 ? 0 : 1);
            Date date = c.getTime();
            String dayOfWeek = new SimpleDateFormat("EEEE").format(date);
            String dateNumb = date.getDate() + "/" + (date.getMonth()  + 1);
            String dayName = dayDictionary.get(dayOfWeek);
            boolean isToday = i == 0;
            String todayName = i == 0 ? "Hôm nay" : dayName;
            calendars.add(new Calendar(todayName, dateNumb, isToday));
        }
        calendar = calendars.get(0);
    }

    private void getMovieCalendars() {
        AppManager.getInstance().getCommService().getMovieCalendars(TAG_MOVIE_CALENDARS, 1, "1557516579000",
                new DataParser.DataResponseListener<LinkedList<Cinema>>() {
                    @Override
                    public void onDataResponse(LinkedList<Cinema> result) {
                        cinemas = result;

                        txtNotify.setVisibility(View.GONE);
                        expandableListView.setVisibility(View.VISIBLE);
                        listDataChild = new HashMap<>();
                        for (Cinema cinema : cinemas) {
                            List<Showtime> showtimes = Arrays.asList(cinema.getShowtime());
                            listDataChild.put(cinema, showtimes);
                        }
                        expandableListAdapter.setCinemaAndShowtimes(cinemas, listDataChild);

                    }

                    @Override
                    public void onDataError(String errorMessage) {
                        txtNotify.setVisibility(View.VISIBLE);
                        expandableListView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onRequestError(String errorMessage, VolleyError volleyError) {

                    }

                    @Override
                    public void onCancel() {

                    }
                });

    }

    @Override
    public void onCalendarSelected(Calendar calendar) {
        this.calendar = calendar;
        expandableListAdapter.setCalendar(calendar);
    }
}
