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

import java.util.HashMap;
import java.util.Map;

public class SellingTicketActivity extends BaseActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CalendarFragment calendarFragment;
    private Map<Fragment, String> fragmentStringMap;


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
        if (!title.isEmpty()) {
            getSupportActionBar().setTitle(title);
        }

        calendarFragment = new CalendarFragment();
        fragmentStringMap = new HashMap<>();

        fragmentStringMap.put(calendarFragment, "Lịch chiếu");

        setupViewPager(viewPager, fragmentStringMap);
        tabLayout.setupWithViewPager(viewPager);
    }




}
