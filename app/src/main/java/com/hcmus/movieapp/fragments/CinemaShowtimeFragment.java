package com.hcmus.movieapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.activities.SellingTicketActivity;
import com.hcmus.movieapp.adapters.CinemaExpandableListAdapter;
import com.hcmus.movieapp.adapters.HorizontalCalendarAdapter;
import com.hcmus.movieapp.models.Calendar;
import com.hcmus.movieapp.models.Cinema;
import com.hcmus.movieapp.models.Movie;
import com.hcmus.movieapp.models.Showtime;
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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CinemaShowtimeFragment extends Fragment implements HorizontalCalendarAdapter.ItemCalendarListener {
    private ExpandableListView expandableListView;
    private CinemaExpandableListAdapter cinemaExpandableListAdapter;
    private HorizontalCalendarAdapter horizontalCalendarAdapter;
    private TextView txtNotify;
    private RecyclerView rvCalendar;
    private RecyclerView.LayoutManager rvCalendarLayoutManager;

    private List<Movie> movies;
    private List<Calendar> calendars;
    private HashMap<Movie, List<Showtime>> listDataChild;
    private Cinema cinema;
    private Calendar calendar;

    private static final String TAG_CINEMA_SHOWTIMES = "TAG_CINEMA_SHOWTIMES";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calendar_fragment, container, false);

        rvCalendar = v.findViewById(R.id.rv_calendar);
        expandableListView = v.findViewById(R.id.list_expand);
        txtNotify = v.findViewById(R.id.txtNotify);

        setupData();
        setupExpandableListView();

        rvCalendarLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvCalendar.setLayoutManager(rvCalendarLayoutManager);
        horizontalCalendarAdapter = new HorizontalCalendarAdapter(calendars, getContext());
        horizontalCalendarAdapter.setListener(this);
        rvCalendar.setAdapter(horizontalCalendarAdapter);

        return v;
    }

    @Override
    public void onCalendarSelected(Calendar calendar) {
        this.calendar = calendar;
        if (cinema != null) {
            getCinemaShowtimes();
            cinemaExpandableListAdapter.setCalendar(calendar);
        }
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    private void getCinemaShowtimes() {
        String timeStamp = String.valueOf(calendar.getDate().getTime());
//        String timeStamp = "1560124800000";
        AppManager.getInstance().getCommService().getCinemaShowtimes(TAG_CINEMA_SHOWTIMES, cinema.getId(), timeStamp,
                new DataParser.DataResponseListener<LinkedList<Movie>>() {
                    @Override
                    public void onDataResponse(LinkedList<Movie> result) {
                        movies.clear();
                        movies = result;

                        txtNotify.setVisibility(View.GONE);
                        expandableListView.setVisibility(View.VISIBLE);
                        listDataChild = new HashMap<>();
                        for (Movie movie : movies) {
                            List<Showtime> showtimes = Arrays.asList(movie.getShowtimes());
                            listDataChild.put(movie, showtimes);
                        }
                        cinemaExpandableListAdapter.setMovieAndShowtimes(movies, listDataChild);

                        // Expand all groups one by one
                        for(int i=0; i < cinemaExpandableListAdapter.getGroupCount(); i++) {
                            expandableListView.expandGroup(i);
                        }

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

    private void setupExpandableListViewForMovie() {
        cinemaExpandableListAdapter = new CinemaExpandableListAdapter(getContext(), movies, listDataChild);
        cinemaExpandableListAdapter.setCinema(cinema);
        expandableListView.setAdapter(cinemaExpandableListAdapter);
        cinemaExpandableListAdapter.setCalendar(calendar);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Intent intent = new Intent(getContext(), SellingTicketActivity.class);
                Gson gson = new Gson();
                String json = gson.toJson(movies.get(groupPosition));
                intent.putExtra("title", movies.get(groupPosition).getName());
                intent.putExtra("movie", json);
                intent.putExtra("tabSelected", 1);
                getContext().startActivity(intent);

                return true; // This way the expander cannot be collapsed
            }
        });
    }

    private void setupExpandableListView() {
        if (cinema != null) {
            setupExpandableListViewForMovie();
        }
    }

    private void setupData() {
        movies = new ArrayList<>();
        listDataChild = new HashMap<>();

        initCalendarTime();
        if (cinema != null) {
            getCinemaShowtimes();
        }
    }
}
