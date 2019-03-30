package com.example.movieapp.activities;

import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Display;
import android.widget.ExpandableListView;

import com.example.movieapp.R;
import com.example.movieapp.adapters.ExpandableListAdapter;
import com.example.movieapp.adapters.ViewPagerHomeAdapter;
import com.example.movieapp.models.Cinema;
import com.example.movieapp.models.Showtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SellingTicketActivity extends BaseActivity {

    private Toolbar toolbar;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private List<Cinema> cinemas;
    private HashMap<Cinema, List<Showtime>> listDataChild;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selling_ticket_activity);

        toolbar = findViewById(R.id.toolbar);
        expandableListView = findViewById(R.id.list_expand);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupDummyData();
        adapter = new ExpandableListAdapter(this, cinemas, listDataChild);
        expandableListView.setAdapter(adapter);

        Display display = getWindowManager().getDefaultDisplay();
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
    }
}
