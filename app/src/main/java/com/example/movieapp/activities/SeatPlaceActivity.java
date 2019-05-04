package com.example.movieapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.R;
import com.example.movieapp.models.Calendar;
import com.example.movieapp.models.Cinema;
import com.example.movieapp.models.Movie;
import com.example.movieapp.models.SeatMo;
import com.example.movieapp.models.Showtime;
import com.example.movieapp.models.Ticket;
import com.example.movieapp.utils.Constant;
import com.example.movieapp.utils.CustomDeserializer;
import com.example.movieapp.utils.Utilities;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.captain_miao.seatview.BaseSeatMo;
import com.github.captain_miao.seatview.MovieSeatView;
import com.github.captain_miao.seatview.SeatPresenter;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SeatPlaceActivity extends BaseActivity implements SeatPresenter {

    private static final String TAG = "SelectMovieSeat";
    private static final int MAX_SEATS = 5;

    private MovieSeatView mMovieSeatView;
    private SeatMo[][] seatTable;

    public List<SeatMo> selectedSeats;
    private TextView txtTotalPrice, txtContinue;
    private TextView  txtCinema, txtBranch, txtDate,
            txtTime, txtRoomNumber, txtCurrentTime;
    private ImageView imgClose;

    private int maxRow = 10;
    private int maxColumn = 12;

    private Cinema cinema;
    private Calendar calendar;
    private Movie movie;
    private Showtime showtime;

    @JsonDeserialize(keyUsing = CustomDeserializer.class, keyAs = Ticket.class)
    private Map<Ticket, Integer> map = new HashMap<>();
    private Gson gson = new Gson();
    private boolean isNormal, isVip, isCouple;
    private long currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_place);


        selectedSeats = new ArrayList<>();
        mMovieSeatView = findViewById(R.id.seat_view);
        txtContinue = findViewById(R.id.txtContinue);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        txtCinema = findViewById(R.id.txtCinema);
        txtBranch = findViewById(R.id.txtBranch);
        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        txtRoomNumber = findViewById(R.id.txtRoomNumber);
        txtCurrentTime = findViewById(R.id.txtCurrentTime);
        imgClose = findViewById(R.id.imgClose);


        getDataFromPreviousActivity();
        initSeatTable();
        setupUIData();

        txtContinue.setOnClickListener(v -> {
            if (selectedSeats.size() == 0) {
                showDialogErrorWithOKButton(SeatPlaceActivity.this, "Lỗi", "Vui lòng chọn ghế để tiến hành thanh toán");
                return;
            }
            Intent intent = new Intent(SeatPlaceActivity.this, ConfirmationActivity.class);
            String jsonMovie = gson.toJson(movie);
            String jsonShowtime = gson.toJson(showtime);
            String jsonCinema = gson.toJson(cinema);
            String jsonCalendar = gson.toJson(calendar);

            intent.putExtra("movie", jsonMovie);
            intent.putExtra("boughtTicketMap", (Serializable) map);
            intent.putExtra("cinema", jsonCinema);
            intent.putExtra("showtime", jsonShowtime);
            intent.putExtra("calendar", jsonCalendar);
            intent.putExtra("timer", currentTime);
            intent.putExtra("selectedSeat", (Serializable) selectedSeats);
            startActivity(intent);
        });
    }

    private void getDataFromPreviousActivity() {
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
        map = (Map<Ticket, Integer>) getIntent().getSerializableExtra("boughtTicketMap");
    }

    private void setupUIData() {
        txtCinema.setText(cinema.getName());
        String roomName = showtime.getBranchName().substring(showtime.getBranchName().lastIndexOf(" ") + 1);
        txtBranch.setText(" - " + roomName);
        txtDate.setText(calendar.getName());
        txtTime.setText(" - " + showtime.getTimeStart());
        txtRoomNumber.setVisibility(View.GONE);

        new CountDownTimer(300000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtCurrentTime.setText(new SimpleDateFormat("mm:ss").format(new Date(millisUntilFinished)));
                currentTime = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                Toast.makeText(SeatPlaceActivity.this, "Hết thời gian đặt vé", Toast.LENGTH_SHORT).show();
            }
        }.start();

        mMovieSeatView.setSeatTable(seatTable);
        mMovieSeatView.setPresenter(this);

        double totalPrice = 0;
        for (Map.Entry<Ticket, Integer> entry : map.entrySet()) {
            Ticket ticket = entry.getKey();
            int quantity = entry.getValue();
            totalPrice += ticket.getPrice() * quantity;
        }
        txtTotalPrice.setText(Utilities.formatCurrency(totalPrice));
        imgClose.setOnClickListener(v -> showDialogErrorWithOKButtonListener(SeatPlaceActivity.this,
                "Thông báo",
                "Bạn có chắc chắn muốn hủy giao dịch này?",
                (dialog, which) -> finish()));
    }


    private void initSeatTable() {
        seatTable = new SeatMo[maxRow][maxColumn];// mock data
        enableSeatPos();
        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxColumn; j++) {
                SeatMo seat = new SeatMo();
                seat.row = i;
                seat.column = j;
                seat.rowName = String.valueOf((char)('A' + i));
                seat.seatName = seat.rowName + " Row" + (j + 1) + " Seat";
                seat.status = 0;
                if (i < Constant.SeatTypePosition.NORMAL && isNormal) {
                    seat.status = 1;
                    seat.setTypeSeat(Constant.SeatTypePosition.NORMAL);
                }
                if (i >= Constant.SeatTypePosition.NORMAL && i < Constant.SeatTypePosition.VIP && isVip) {
                    seat.status = 1;
                    seat.setTypeSeat(Constant.SeatTypePosition.VIP);
                }
                if (i >= Constant.SeatTypePosition.VIP && i < Constant.SeatTypePosition.COUPLE && isCouple) {
                    seat.status = 1;
                    seat.setTypeSeat(Constant.SeatTypePosition.COUPLE);
                }

                if (j == 3 || j == 8) {
                    seat = null;
                }
                seatTable[i][j] = seat;
            }
        }
    }

    private Map.Entry<Ticket, Integer> getTicketByType(int type) {
        for (Map.Entry<Ticket, Integer> entry : map.entrySet()) {
            Ticket ticket = entry.getKey();
            if (ticket.getId() == type) {
                return entry;
            }
        }
        return null;
    }

    private void enableSeatPos() {
        List<Integer> seatTypes = getSeatTypes();
        for (int i = 0; i < seatTypes.size(); i++) {
            if (seatTypes.get(i) == Constant.SeatType.NORMAL) {
                isNormal = true;
            }
            if (seatTypes.get(i) == Constant.SeatType.VIP) {
                isVip = true;
            }
            if (seatTypes.get(i) == Constant.SeatType.COUPLE) {
                isCouple = true;
            }
        }
    }

    private List<Integer> getSeatTypes() {
        List<Integer> types = new ArrayList<>();
        for (Ticket ticket : map.keySet()) {
            types.add(ticket.getId());
        }
        return types;
    }

    private String getTypeName(int type) {
        switch (type) {
            case Constant.SeatType.NORMAL:
                return "ghế thường";
            case Constant.SeatType.COUPLE:
                return "ghế đôi";
            case Constant.SeatType.VIP:
                return "ghế VIP";
            default:
                return "";
        }
    }

    private int getTotalSeatCanSelect() {
        int sum = 0;
        List<Integer> types = new ArrayList<>();
        for (int i : map.values()) {
            sum += i;
        }
        return sum;
    }

    private int getTotalSelectedSeatByType(int type) {
        int sum = 0;
        for (SeatMo seatMo : selectedSeats) {
            if (seatMo.getTypeSeat() == type) {
                sum++;
            }
        }
        return sum;
    }


    @Override
    public boolean onClickSeat(int row, int column, BaseSeatMo seat) {
        SeatMo seatMo = seatTable[row][column];

        if(seatMo != null){
            if (seatMo.isOnSale()) {
                int max_seats = MAX_SEATS;
                int type = 0;
                if (row < Constant.SeatTypePosition.NORMAL && isNormal) {
                    type = Constant.SeatType.NORMAL;
                }
                if (row >= Constant.SeatTypePosition.NORMAL && row < Constant.SeatTypePosition.VIP && isVip) {
                    type = Constant.SeatType.VIP;
                }
                if (row >= Constant.SeatTypePosition.VIP && row < Constant.SeatTypePosition.COUPLE && isCouple) {
                    type = Constant.SeatType.COUPLE;
                }
                Map.Entry<Ticket, Integer> entry = getTicketByType(type);
                int maxSeatByType = entry.getValue();
                int totalSelectedSeatByType = getTotalSelectedSeatByType(seatMo.getTypeSeat());
                max_seats = getTotalSeatCanSelect();
                if (selectedSeats.size() < max_seats && totalSelectedSeatByType < maxSeatByType) {
                    seatMo.setSelected();
                    selectedSeats.add(seatMo);
                    return true;
                } else {
                    if (totalSelectedSeatByType >= maxSeatByType) {
                        Toast.makeText(this, getString(R.string.seat_max_number_by_type, maxSeatByType, getTypeName(type)), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, getString(R.string.seat_max_number, max_seats), Toast.LENGTH_SHORT).show();
                    }

                }

            } else if (seatMo.isSelected()) {
                seatMo.setOnSale();
                selectedSeats.remove(seatMo);
                return true;
            }
        }
        return false;
    }
}
