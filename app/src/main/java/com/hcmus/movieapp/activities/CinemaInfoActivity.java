package com.hcmus.movieapp.activities;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.fragments.CinemaInfoFragment;
import com.hcmus.movieapp.fragments.CinemaShowtimeFragment;
import com.hcmus.movieapp.models.Cinema;

import java.util.LinkedHashMap;
import java.util.Map;

public class CinemaInfoActivity extends BaseActivity {

    private Toolbar toolbar;
    protected ViewPager viewPager;
    protected TabLayout tabLayout;
    protected CinemaShowtimeFragment cinemaShowtimeFragment;
    protected CinemaInfoFragment cinemaInfoFragment;
    protected Map<Fragment, String> fragmentStringMap;
    protected Gson gson = new Gson();
    private Cinema cinema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selling_ticket_activity);

        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getIntent().getStringExtra("title");
        String cinemaJson = getIntent().getStringExtra("cinema");
        if (!title.isEmpty()) {
            getSupportActionBar().setTitle(title);
        }
        if (cinemaJson != null && !cinemaJson.isEmpty()) {
            cinema = gson.fromJson(cinemaJson, Cinema.class);
        }

        fragmentStringMap = new LinkedHashMap<>();
        cinemaInfoFragment = new CinemaInfoFragment();
        cinemaShowtimeFragment = new CinemaShowtimeFragment();

        cinemaInfoFragment.setCinema(cinema);
        cinemaShowtimeFragment.setCinema(cinema);

        fragmentStringMap.put(cinemaShowtimeFragment, "Lịch chiếu");
        fragmentStringMap.put(cinemaInfoFragment, "Thông tin");

        setupViewPager(viewPager, fragmentStringMap);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
