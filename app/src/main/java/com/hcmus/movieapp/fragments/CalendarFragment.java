package com.hcmus.movieapp.fragments;

import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.adapters.ExpandableListAdapter;
import com.hcmus.movieapp.adapters.HorizontalCalendarAdapter;
import com.hcmus.movieapp.models.Calendar;
import com.hcmus.movieapp.models.Cinema;
import com.hcmus.movieapp.models.Movie;
import com.hcmus.movieapp.models.ShowMatch;
import com.hcmus.movieapp.models.Showtime;
import com.hcmus.movieapp.models.Sport;
import com.hcmus.movieapp.models.Stadium;
import com.hcmus.movieapp.utils.AppManager;
import com.hcmus.movieapp.utils.DataParser;

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
    private List<Stadium> stadiums;
    private List<Calendar> calendars;
    private HashMap<Cinema, List<Showtime>> listDataChild;
    private HashMap<Stadium, List<ShowMatch>> listHashMap;
    private Movie movie;
    private Sport sport;
    private static final String TAG_MOVIE_CALENDARS = "TAG_MOVIE_CALENDARS";
    private static final String TAG_EVENT_CALENDARS = "TAG_EVENT_CALENDARS";
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
        txtNotify.setText("Không có lịch bán vé cho ngày đã chọn");

        setupDummyData();
        setupExpandableListView();

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

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    private void setupDummyData() {
        cinemas = new ArrayList<>();
        stadiums = new ArrayList<>();
        listDataChild = new HashMap<>();

        initCalendarTime();
        if (movie != null) {
            getMovieCalendars();
        } else {
            getSportCalendars();
        }
    }

    private void setupExpandableListView() {
        if (movie != null) {
            setupExpandableListViewForMovie();
        } else {
            setupExpandableListViewForSport();
        }
    }

    private void setupExpandableListViewForMovie() {
        expandableListAdapter = new ExpandableListAdapter(getContext(), cinemas, listDataChild);
        expandableListAdapter.setMovie(movie);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListAdapter.setCalendar(calendar);
    }

    private void setupExpandableListViewForSport() {
        expandableListAdapter = new ExpandableListAdapter(getContext(), stadiums, listHashMap, 1);
        expandableListAdapter.setSport(sport);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListAdapter.setCalendar(calendar);
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
            calendars.add(new Calendar(todayName, dateNumb, isToday, date));
        }
        calendar = calendars.get(0);
    }

    private void getSportCalendars() {
        String timeStamp = String.valueOf(calendar.getDate().getTime());
//        String timeStamp = "1560470400000";
        AppManager.getInstance().getCommService().getEventCalendars(TAG_EVENT_CALENDARS, sport.getId(), timeStamp,
                new DataParser.DataResponseListener<LinkedList<Stadium>>() {
            @Override
            public void onDataResponse(LinkedList<Stadium> result) {
                stadiums.clear();
                stadiums = result;
                txtNotify.setVisibility(View.GONE);
                expandableListView.setVisibility(View.VISIBLE);
                listHashMap = new HashMap<>();
                for (Stadium stadium : stadiums) {
                    List<ShowMatch> showMatches = Arrays.asList(stadium.getShowMatches());
                    listHashMap.put(stadium, showMatches);
                }
                expandableListAdapter.setStadumAndShowTimes(stadiums, listHashMap);
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

    private void getMovieCalendars() {
        String timeStamp = String.valueOf(calendar.getDate().getTime());
        //String timeStamp = "1560267586000";
        AppManager.getInstance().getCommService().getMovieCalendars(TAG_MOVIE_CALENDARS, movie.getId(), timeStamp,
                new DataParser.DataResponseListener<LinkedList<Cinema>>() {
                    @Override
                    public void onDataResponse(LinkedList<Cinema> result) {
                        cinemas.clear();
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
        if (movie != null) {
            getMovieCalendars();
        } else {
            getSportCalendars();
        }
        expandableListAdapter.setCalendar(calendar);
    }
}
