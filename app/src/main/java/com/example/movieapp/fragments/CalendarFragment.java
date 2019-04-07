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

import com.example.movieapp.R;
import com.example.movieapp.adapters.ExpandableListAdapter;
import com.example.movieapp.adapters.HorizontalCalendarAdapter;
import com.example.movieapp.models.Calendar;
import com.example.movieapp.models.Cinema;
import com.example.movieapp.models.Showtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CalendarFragment extends Fragment {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private HorizontalCalendarAdapter horizontalCalendarAdapter;
    private RecyclerView rvCalendar;
    private RecyclerView.LayoutManager rvCalendarLayoutManager;
    private List<Cinema> cinemas;
    private List<Calendar> calendars;
    private HashMap<Cinema, List<Showtime>> listDataChild;

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


        setupDummyData();
        expandableListAdapter = new ExpandableListAdapter(getContext(), cinemas, listDataChild);
        expandableListView.setAdapter(expandableListAdapter);

        rvCalendarLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvCalendar.setLayoutManager(rvCalendarLayoutManager);
        horizontalCalendarAdapter = new HorizontalCalendarAdapter(calendars, getContext());
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

    private void setupDummyData() {
        cinemas = new ArrayList<>();
        listDataChild = new HashMap<>();

        cinemas.add(new Cinema(1, "BHD Star Cineplex",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSGWHjPhOOr0TpaclExnfdE1gAtAM_22VuZ852BIossve0EPxMQ",
                "321 Nguyễn Văn Cừ P1 Q5 TPHCM", ""));
        cinemas.add(new Cinema(2, "BHD Star Cineplex",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSGWHjPhOOr0TpaclExnfdE1gAtAM_22VuZ852BIossve0EPxMQ",
                "321 Nguyễn Văn Cừ P1 Q5 TPHCM", ""));
        cinemas.add(new Cinema(3, "BHD Star Cineplex",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSGWHjPhOOr0TpaclExnfdE1gAtAM_22VuZ852BIossve0EPxMQ",
                "321 Nguyễn Văn Cừ P1 Q5 TPHCM", ""));
        cinemas.add(new Cinema(4, "BHD Star Cineplex",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSGWHjPhOOr0TpaclExnfdE1gAtAM_22VuZ852BIossve0EPxMQ",
                "321 Nguyễn Văn Cừ P1 Q5 TPHCM", ""));

        List<Showtime> showtimes = new ArrayList<>();
        showtimes.add(new Showtime("BHD - 3/2", "11:20", "14:20",
                "2D - Phụ đề", "70k", "2km"));

        showtimes.add(new Showtime("BHD - 3/2", "11:20", "14:20",
                "2D - Phụ đề", "70k", "2km"));

        showtimes.add(new Showtime("BHD - 3/2", "11:20", "14:20",
                "2D - Phụ đề", "70k", "2km"));

        showtimes.add(new Showtime("BHD - 3/2", "11:20", "14:20",
                "2D - Phụ đề", "70k", "2km"));

        showtimes.add(new Showtime("BHD - 3/2", "11:20", "14:20",
                "2D - Phụ đề", "70k", "2km"));

        listDataChild.put(cinemas.get(0), showtimes);
        listDataChild.put(cinemas.get(1), showtimes);
        listDataChild.put(cinemas.get(2), showtimes);
        listDataChild.put(cinemas.get(3), showtimes);

        calendars = new ArrayList<>();
        calendars.add(new Calendar("Hôm nay", "24", true));
        calendars.add(new Calendar("Thứ hai", "25", false));
        calendars.add(new Calendar("Thứ ba", "26", false));
        calendars.add(new Calendar("Thứ tư", "27", false));
        calendars.add(new Calendar("Thứ năm", "28", false));
        calendars.add(new Calendar("Thứ sáu", "29", false));
        calendars.add(new Calendar("Thứ bảy", "30", false));

    }
}
