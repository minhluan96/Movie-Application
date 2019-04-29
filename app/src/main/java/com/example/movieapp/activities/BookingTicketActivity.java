package com.example.movieapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.adapters.TicketTypeAdapter;
import com.example.movieapp.models.Cinema;
import com.example.movieapp.models.Movie;
import com.example.movieapp.models.Showtime;
import com.example.movieapp.models.Ticket;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class BookingTicketActivity extends BaseActivity implements TicketTypeAdapter.TicketChangeListener {

    private TextView txtNameMovie, txtMinAge, txtDetailDurationAndType,
            txtTotalPrice, txtContinue, txtCinema,
            txtBranch, txtDate, txtTime, txtRoomNumber;
    private RecyclerView rvTicketTypes;
    private ImageView imgClose;
    private RecyclerView.LayoutManager layoutManager;
    private TicketTypeAdapter ticketTypeAdapter;
    private Movie movie;
    private Showtime showtime;
    private Cinema cinema;
    private Gson gson = new Gson();

    private double totalPrice = 0;

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

        getDataFromPreviousIntent();
        setupUIData();

        layoutManager = new LinearLayoutManager(this);
        rvTicketTypes.setLayoutManager(layoutManager);
        setupTicketTypeAdapter();

        txtTotalPrice.setText(String.valueOf(totalPrice));

        txtContinue.setOnClickListener(v -> {
            Intent intent = new Intent(BookingTicketActivity.this, SeatPlaceActivity.class);
            startActivity(intent);
        });

        imgClose.setOnClickListener(v -> finish());
    }

    private void getDataFromPreviousIntent() {
        String movieJson = getIntent().getStringExtra("movie");
        String showtimeJson = getIntent().getStringExtra("showtime");
        String cinemaJson = getIntent().getStringExtra("cinema");
        if (!movieJson.isEmpty()) {
            movie = gson.fromJson(movieJson, Movie.class);
        }
        if (!showtimeJson.isEmpty()) {
            showtime = gson.fromJson(showtimeJson, Showtime.class);
        }
        if (!cinemaJson.isEmpty()) {
            cinema = gson.fromJson(cinemaJson, Cinema.class);
        }
    }

    private void setupUIData() {
        txtCinema.setText(cinema.getName());
        String roomName = showtime.getBranchName().substring(showtime.getBranchName().lastIndexOf(" ") + 1);
        txtBranch.setText(" - " + roomName);
    }

    private void setupTicketTypeAdapter() {
        List<Ticket> tickets = getDummyData();
        ticketTypeAdapter = new TicketTypeAdapter(tickets, this);
        ticketTypeAdapter.setTicketChangeListener(this);
        rvTicketTypes.setAdapter(ticketTypeAdapter);
    }

    private List<Ticket> getDummyData() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket(1, "Vé thường 2D", 90000));
        tickets.add(new Ticket(1, "Vé VIP 2D", 100000));
        tickets.add(new Ticket(1, "Ghế đôi 2D", 120000));
        return tickets;
    }

    @Override
    public void onAddButtonChanged(int totalQuantity, double pricePerTicket) {
        totalPrice += pricePerTicket;
        txtTotalPrice.setText(String.valueOf(totalPrice) + " đ");
        ticketTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSubtractButtonChanged(int totalQuantity, double pricePerTicket) {
        totalPrice -= pricePerTicket;
        txtTotalPrice.setText(String.valueOf(totalPrice) + " đ");
        ticketTypeAdapter.notifyDataSetChanged();
    }
}
