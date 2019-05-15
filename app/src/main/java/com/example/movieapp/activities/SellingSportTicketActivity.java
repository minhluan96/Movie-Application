package com.example.movieapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.movieapp.R;
import com.example.movieapp.models.Sport;

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
    }
}
