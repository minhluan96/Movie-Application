package com.example.movieapp.activities;

import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.movieapp.R;
import com.example.movieapp.fragments.CalendarFragment;
import com.example.movieapp.fragments.InfoFragment;
import com.example.movieapp.models.Movie;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SellingTicketActivity extends BaseActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CalendarFragment calendarFragment;
    private InfoFragment infoFragment;
    private Map<Fragment, String> fragmentStringMap;
    private Gson gson = new Gson();
    private Movie movie;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selling_ticket_activity);

        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getIntent().getStringExtra("title");
        String movieJson = getIntent().getStringExtra("movie");
        if (!title.isEmpty()) {
            getSupportActionBar().setTitle(title);
        }
        if (!movieJson.isEmpty()) {
            movie = gson.fromJson(movieJson, Movie.class);
        }

        fragmentStringMap = new LinkedHashMap<>();
        InfoFragment infoFragment = new InfoFragment();
        CalendarFragment calendarFragment = new CalendarFragment();
        infoFragment.setMovie(movie);
        calendarFragment.setMovie(movie);

        fragmentStringMap.put(calendarFragment, "Lịch chiếu");
        fragmentStringMap.put(infoFragment, "Thông tin");

        setupViewPager(viewPager, fragmentStringMap);
        tabLayout.setupWithViewPager(viewPager);
    }

}
