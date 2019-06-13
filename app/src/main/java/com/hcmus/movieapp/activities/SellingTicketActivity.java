package com.hcmus.movieapp.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.fragments.CalendarFragment;
import com.hcmus.movieapp.fragments.InfoFragment;
import com.hcmus.movieapp.models.Movie;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

public class SellingTicketActivity extends BaseActivity {

    private Toolbar toolbar;
    protected ViewPager viewPager;
    protected TabLayout tabLayout;
    protected CalendarFragment calendarFragment;
    protected InfoFragment infoFragment;
    protected Map<Fragment, String> fragmentStringMap;
    protected Gson gson = new Gson();
    private Movie movie;
    private static int tabSelected = 0;

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
        tabSelected = getIntent().getIntExtra("tabSelected", 0);
        if (!title.isEmpty()) {
            getSupportActionBar().setTitle(title);
        }
        if (movieJson != null && !movieJson.isEmpty()) {
            movie = gson.fromJson(movieJson, Movie.class);
        }

        fragmentStringMap = new LinkedHashMap<>();
        infoFragment = new InfoFragment();calendarFragment = new CalendarFragment();
        infoFragment.setMovie(movie);
        calendarFragment.setMovie(movie);

        fragmentStringMap.put(calendarFragment, "Lịch chiếu");
        fragmentStringMap.put(infoFragment, "Thông tin");

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

    @Override
    public void onResume() {
        super.onResume();
        TabLayout.Tab tab = tabLayout.getTabAt(tabSelected);
        tab.select();
    }
}
