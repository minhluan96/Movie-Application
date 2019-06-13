package com.hcmus.movieapp.activities;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.fragments.CalendarFragment;
import com.hcmus.movieapp.fragments.CinemaShowtimeFragment;
import com.hcmus.movieapp.fragments.StadiumShowmatchFragment;
import com.hcmus.movieapp.fragments.VenueInfoFragment;
import com.hcmus.movieapp.models.Venue;

import java.util.LinkedHashMap;
import java.util.Map;

public class VenueInfoActivity extends BaseActivity {

    private Toolbar toolbar;
    protected ViewPager viewPager;
    protected TabLayout tabLayout;
    protected StadiumShowmatchFragment stadiumShowmatchFragment;
    protected VenueInfoFragment venueInfoFragment;
    protected Map<Fragment, String> fragmentStringMap;
    protected Gson gson = new Gson();
    private Venue venue;

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
        String venueJson = getIntent().getStringExtra("venue");
        if (!title.isEmpty()) {
            getSupportActionBar().setTitle(title);
        }
        if (venueJson != null && !venueJson.isEmpty()) {
            venue = gson.fromJson(venueJson, Venue.class);
        }

        fragmentStringMap = new LinkedHashMap<>();
        venueInfoFragment = new VenueInfoFragment();
        stadiumShowmatchFragment = new StadiumShowmatchFragment();

        venueInfoFragment.setVenue(venue);
        stadiumShowmatchFragment.setVenue(venue);

        fragmentStringMap.put(stadiumShowmatchFragment, "Lịch bán vé");
        fragmentStringMap.put(venueInfoFragment, "Thông tin");

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
