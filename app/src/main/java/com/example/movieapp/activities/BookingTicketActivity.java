package com.example.movieapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.adapters.TicketTypeAdapter;
import com.example.movieapp.models.Ticket;

import java.util.ArrayList;
import java.util.List;

public class BookingTicketActivity extends AppCompatActivity {

    private TextView txtNameMovie, txtMinAge, txtDetailDurationAndType,
            txtTotalPrice, txtContinue, txtCinema,
            txtBranch, txtDate, txtTime, txtRoomNumber;
    private RecyclerView rvTicketTypes;
    private ImageView imgClose;
    private RecyclerView.LayoutManager layoutManager;
    private TicketTypeAdapter ticketTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_ticket);
        txtNameMovie = findViewById(R.id.txtNameMovie);
        txtMinAge = findViewById(R.id.txtMinAge);
        txtDetailDurationAndType = findViewById(R.id.txtDetailMovieDurationAndType);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        txtContinue = findViewById(R.id.txtContinue);
        txtBranch = findViewById(R.id.txtBranch);
        txtDate = findViewById(R.id.txtDate);
        txtCinema = findViewById(R.id.txtCinema);
        txtTime = findViewById(R.id.txtTime);
        txtRoomNumber = findViewById(R.id.txtRoomNumber);
        imgClose = findViewById(R.id.imgClose);
        rvTicketTypes = findViewById(R.id.rv_ticket_type);

        layoutManager = new LinearLayoutManager(this);
        rvTicketTypes.setLayoutManager(layoutManager);
        setupTicketTypeAdapter();
    }

    private void setupTicketTypeAdapter() {
        List<Ticket> tickets = getDummyData();
        ticketTypeAdapter = new TicketTypeAdapter(tickets, this);
        rvTicketTypes.setAdapter(ticketTypeAdapter);
    }

    private List<Ticket> getDummyData() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket(1, "Vé thường 2D", 90000));
        tickets.add(new Ticket(1, "Vé VIP 2D", 100000));
        tickets.add(new Ticket(1, "Ghế đôi 2D", 120000));
        return tickets;
    }
}
