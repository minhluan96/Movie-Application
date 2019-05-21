package com.example.movieapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ResultTicketSportActivity extends ResultTicketActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initialGUI() {
        super.initialGUI();
        txtTitle.setText(ticketInfo.getEventName());
        txtMinAge.setText(ticketInfo.getGateway());
        txtDescription.setText(ticketInfo.getBlock());
        txtType.setText(ticketInfo.getAddress());
    }
}
