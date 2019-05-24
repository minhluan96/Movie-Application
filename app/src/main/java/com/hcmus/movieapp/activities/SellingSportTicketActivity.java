package com.hcmus.movieapp.activities;

import android.os.Bundle;

import com.hcmus.movieapp.models.Sport;

public class SellingSportTicketActivity extends SellingTicketActivity {

    private Sport sport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String sportJson = getIntent().getStringExtra("sport");
        if (!sportJson.isEmpty()) {
            sport = gson.fromJson(sportJson, Sport.class);
        }

        infoFragment.setSport(sport);
        calendarFragment.setSport(sport);

        fragmentStringMap.clear();
        fragmentStringMap.put(calendarFragment, "Lịch thi đấu");
        fragmentStringMap.put(infoFragment, "Thông tin");

        setupViewPager(viewPager, fragmentStringMap);
        tabLayout.setupWithViewPager(viewPager);
    }


}
