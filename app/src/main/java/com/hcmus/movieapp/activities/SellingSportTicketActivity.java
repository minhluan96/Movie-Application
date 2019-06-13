package com.hcmus.movieapp.activities;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.hcmus.movieapp.models.Sport;

public class SellingSportTicketActivity extends SellingTicketActivity {

    private Sport sport;
    private static int tabSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String sportJson = getIntent().getStringExtra("sport");
        tabSelected = getIntent().getIntExtra("tabSelected", 0);
        if (!sportJson.isEmpty()) {
            sport = gson.fromJson(sportJson, Sport.class);
        }

        infoFragment.setSport(sport);
        calendarFragment.setSport(sport);

        fragmentStringMap.clear();
        fragmentStringMap.put(calendarFragment, "Lịch bán vé");
        fragmentStringMap.put(infoFragment, "Thông tin");

        setupViewPager(viewPager, fragmentStringMap);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
        TabLayout.Tab tab = tabLayout.getTabAt(tabSelected);
        tab.select();
    }
}
