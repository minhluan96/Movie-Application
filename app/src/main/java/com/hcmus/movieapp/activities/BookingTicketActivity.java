package com.hcmus.movieapp.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.adapters.TicketTypeAdapter;
import com.hcmus.movieapp.models.Calendar;
import com.hcmus.movieapp.models.Cinema;
import com.hcmus.movieapp.models.Movie;
import com.hcmus.movieapp.models.ShowMatch;
import com.hcmus.movieapp.models.Showtime;
import com.hcmus.movieapp.models.Sport;
import com.hcmus.movieapp.models.Stadium;
import com.hcmus.movieapp.models.Ticket;
import com.hcmus.movieapp.utils.Utilities;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingTicketActivity extends BaseActivity implements TicketTypeAdapter.TicketChangeListener {

    private TextView txtNameMovie, txtMinAge, txtDetailDurationAndType,
            txtTotalPrice, txtContinue, txtCinema,
            txtBranch, txtDate, txtTime, txtRoomNumber, txtCurrentTime;
    private RecyclerView rvTicketTypes;
    private ImageView imgClose, imgPoster;
    private RecyclerView.LayoutManager layoutManager;
    private TicketTypeAdapter ticketTypeAdapter;
    private Movie movie;
    private Calendar calendar;
    private Showtime showtime;
    private Cinema cinema;
    private ShowMatch showMatch;
    private Stadium stadium;
    private Sport sport;
    private Map<Ticket, Integer> boughtTicketList = new HashMap<>();
    private Gson gson = new Gson();
    private boolean isSportEvent = false;

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
        imgPoster = findViewById(R.id.imgPoster);

        getDataFromPreviousIntent();
        setupUIData();

        layoutManager = new LinearLayoutManager(this);
        rvTicketTypes.setLayoutManager(layoutManager);
        setupTicketTypeAdapter();
        txtTotalPrice.setText(Utilities.formatCurrency(totalPrice));

        txtContinue.setOnClickListener(v -> {
            if (totalPrice == 0) {
                showDialogErrorWithOKButton(BookingTicketActivity.this, "Lỗi", "Vui lòng chọn vé để tiến hành đặt ghế");
                return;
            }
            optimizeBoughtTickets();
            if (!isSportEvent) {
                sendMovieData();
            } else {
                sendSportIntent();
            }
        });

        imgClose.setOnClickListener(v -> finish());
    }

    private void sendMovieData() {
        Intent intent = new Intent(BookingTicketActivity.this, SeatPlaceActivity.class);
        String jsonMovie = gson.toJson(movie);
        String jsonShowtime = gson.toJson(showtime);
        String jsonCinema = gson.toJson(cinema);
        String jsonCalendar = gson.toJson(calendar);
        intent.putExtra("movie", jsonMovie);
        intent.putExtra("boughtTicketMap", (Serializable) boughtTicketList);
        intent.putExtra("cinema", jsonCinema);
        intent.putExtra("showtime", jsonShowtime);
        intent.putExtra("calendar", jsonCalendar);
        startActivity(intent);
    }

    private void optimizeBoughtTickets() {
        Map<Ticket, Integer> optimzedMap = new HashMap<>();
        for (Map.Entry<Ticket, Integer> entry : boughtTicketList.entrySet()) {
            if (entry.getValue() != 0) {
                optimzedMap.put(entry.getKey(), entry.getValue());
            }
        }
        boughtTicketList = optimzedMap;
    }

    private void sendSportIntent() {
        Intent intent = new Intent(BookingTicketActivity.this, SeatPlaceSportActivity.class);
        String jsonSport = gson.toJson(sport);
        String jsonShowmatch = gson.toJson(showMatch);
        String jsonStadium = gson.toJson(stadium);
        String jsonCalendar = gson.toJson(calendar);
        intent.putExtra("sport", jsonSport);
        intent.putExtra("boughtTicketMap", (Serializable) boughtTicketList);
        intent.putExtra("stadium", jsonStadium);
        intent.putExtra("showmatch", jsonShowmatch);
        intent.putExtra("calendar", jsonCalendar);
        startActivity(intent);
    }

    private void getDataMovieFromPreviousIntent() {
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

    private void getDataFromPreviousIntent() {
        String movieJson = getIntent().getStringExtra("movie");
        if (movieJson != null && !movieJson.isEmpty()) {
            getDataMovieFromPreviousIntent();
        } else {
            getDataSportFromPreviousIntent();
        }
        String calendarJson = getIntent().getStringExtra("calendar");
        if (!calendarJson.isEmpty()) {
            calendar = gson.fromJson(calendarJson, Calendar.class);
        }
    }

    private void getDataSportFromPreviousIntent() {
        isSportEvent = true;
        String sportJson = getIntent().getStringExtra("sport");
        String showMatchJson = getIntent().getStringExtra("showmatch");
        String stadiumJson = getIntent().getStringExtra("stadium");
        if (!sportJson.isEmpty()) {
            sport = gson.fromJson(sportJson, Sport.class);
        }
        if (!showMatchJson.isEmpty()) {
            showMatch = gson.fromJson(showMatchJson, ShowMatch.class);
        }
        if (!stadiumJson.isEmpty()) {
            stadium = gson.fromJson(stadiumJson, Stadium.class);
        }
    }

    private void setupUIData() {
        if (isSportEvent) {
            setupUIForSportEvent();
        } else {
            setupUIForMovie();
        }
        setTimer();
    }

    private void setupUIForMovie() {
        txtCinema.setText(cinema.getName());
        String roomName = showtime.getBranchName().substring(showtime.getBranchName().lastIndexOf(" ") + 1);
        txtBranch.setText(" - " + roomName);
        txtDate.setText(calendar.getName());
        txtTime.setText(" - " + showtime.getTimeStart());
        txtRoomNumber.setVisibility(View.GONE);
        txtNameMovie.setText(movie.getName());
        txtMinAge.setText(movie.minAgeDisplay());
        txtDetailDurationAndType.setText(movie.getLength() + " - " + showtime.getTypeMovie());
        Picasso.get().load(movie.getImgURL()).error(R.drawable.poster).into(imgPoster);
    }

    private void setTimer() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date d = new Date();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(d);
        txtCurrentTime.setText(dateFormat.format(cal.getTime()));
    }

    private void setupUIForSportEvent() {
        txtCinema.setText(stadium.getName());
        String place = showMatch.getLocation();
        txtBranch.setText(" - " + place);
        txtTime.setText(" - " + showMatch.getTimeStart());
        txtRoomNumber.setVisibility(View.GONE);
        txtNameMovie.setText(sport.getName());
        txtMinAge.setText(showMatch.getTimeStart());
        txtDetailDurationAndType.setText(sport.getDescription());
        Picasso.get().load(sport.getImgUrl()).error(R.drawable.sports).into(imgPoster);
        setTimer();
    }

    private void setupTicketTypeAdapter() {
        List<Ticket> tickets = getDummyData();
        ticketTypeAdapter = new TicketTypeAdapter(tickets, this);
        ticketTypeAdapter.setTicketChangeListener(this);
        rvTicketTypes.setAdapter(ticketTypeAdapter);
        setupPreTicketPlaceHolder();
    }

    private List<Ticket> getDummyData() {
        if (isSportEvent) {
            return getTicketsForSport();
        }
        return getTicketsForMovies();
    }

    private List<Ticket> getTicketsForMovies() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket(1, "Vé thường 2D", 90000));
        tickets.add(new Ticket(2, "Vé VIP 2D", 100000));
        tickets.add(new Ticket(3, "Ghế đôi 2D", 120000));
        return tickets;
    }

    private List<Ticket> getTicketsForSport() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket(1, "Vé thường", 100000));
        tickets.add(new Ticket(2, "Vé VIP", 150000));
        return tickets;
    }

    private void setupPreTicketPlaceHolder() {
        Ticket ticket = ticketTypeAdapter.getTickets().get(1);
        onAddButtonChanged(1, ticket.getPrice(), ticket);
    }

    @Override
    public void onAddButtonChanged(int totalQuantity, double pricePerTicket, Ticket ticket) {
        totalPrice += pricePerTicket;
        boughtTicketList.put(ticket, totalQuantity);
        String formatedPrice = Utilities.formatCurrency(totalPrice);
        txtTotalPrice.setText(formatedPrice);
        ticketTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSubtractButtonChanged(int totalQuantity, double pricePerTicket, Ticket ticket) {
        totalPrice -= pricePerTicket;
        int quantity = boughtTicketList.get(ticket);
        int subtract = totalQuantity--;
        if (subtract <= 0) {
            boughtTicketList.remove(ticket);
        } else {
            boughtTicketList.put(ticket, totalQuantity);
        }

        txtTotalPrice.setText(Utilities.formatCurrency(totalPrice));
        ticketTypeAdapter.notifyDataSetChanged();
    }
}
