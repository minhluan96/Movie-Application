package com.example.movieapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.R;
import com.example.movieapp.adapters.TicketTypeAdapter;
import com.example.movieapp.models.Calendar;
import com.example.movieapp.models.Cinema;
import com.example.movieapp.models.Movie;
import com.example.movieapp.models.Showtime;
import com.example.movieapp.models.Ticket;
import com.example.movieapp.utils.Utilities;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingTicketActivity extends BaseActivity implements TicketTypeAdapter.TicketChangeListener {

    private TextView txtNameMovie, txtMinAge, txtDetailDurationAndType,
            txtTotalPrice, txtContinue, txtCinema,
            txtBranch, txtDate, txtTime, txtRoomNumber, txtCurrentTime;
    private RecyclerView rvTicketTypes;
    private ImageView imgClose;
    private RecyclerView.LayoutManager layoutManager;
    private TicketTypeAdapter ticketTypeAdapter;
    private Movie movie;
    private Calendar calendar;
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
        txtCurrentTime = findViewById(R.id.txtCurrentTime);
        rvTicketTypes = findViewById(R.id.rv_ticket_type);

        getDataFromPreviousIntent();
        setupUIData();

        layoutManager = new LinearLayoutManager(this);
        rvTicketTypes.setLayoutManager(layoutManager);
        setupTicketTypeAdapter();

        txtTotalPrice.setText(String.valueOf(totalPrice));

        txtContinue.setOnClickListener(v -> {
            if (totalPrice == 0) {
                showDialogErrorWithOKButton(BookingTicketActivity.this, "Lỗi", "Vui lòng chọn vé để tiến hành đặt ghế");
                return;
            }

            Intent intent = new Intent(BookingTicketActivity.this, SeatPlaceActivity.class);
            String jsonMovie = gson.toJson(movie);
            String jsonShowtime = gson.toJson(showtime);
            String jsonCinema = gson.toJson(cinema);
            String jsonCalendar = gson.toJson(calendar);
            intent.putExtra("movie", jsonMovie);
            intent.putExtra("cinema", jsonCinema);
            intent.putExtra("showtime", jsonShowtime);
            intent.putExtra("calendar", jsonCalendar);
            startActivity(intent);
        });

        imgClose.setOnClickListener(v -> finish());
    }

    private void getDataFromPreviousIntent() {
        String movieJson = getIntent().getStringExtra("movie");
        String showtimeJson = getIntent().getStringExtra("showtime");
        String cinemaJson = getIntent().getStringExtra("cinema");
        String calendarJson = getIntent().getStringExtra("calendar");
        if (!movieJson.isEmpty()) {
            movie = gson.fromJson(movieJson, Movie.class);
        }
        if (!calendarJson.isEmpty()) {
            calendar = gson.fromJson(calendarJson, Calendar.class);
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
        txtDate.setText(calendar.getName());
        txtTime.setText(" - " + showtime.getTimeStart());
        txtRoomNumber.setVisibility(View.GONE);
        txtNameMovie.setText(movie.getName());
        txtMinAge.setText(movie.minAgeDisplay());
        txtDetailDurationAndType.setText(movie.getLength() + " - " + showtime.getTypeMovie());

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date d = new Date();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(d);
        txtCurrentTime.setText(dateFormat.format(cal.getTime()));
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
        txtTotalPrice.setText(Utilities.formatCurrency(totalPrice));
        ticketTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSubtractButtonChanged(int totalQuantity, double pricePerTicket) {
        totalPrice -= pricePerTicket;
        txtTotalPrice.setText(Utilities.formatCurrency(totalPrice));
        ticketTypeAdapter.notifyDataSetChanged();
    }
}
