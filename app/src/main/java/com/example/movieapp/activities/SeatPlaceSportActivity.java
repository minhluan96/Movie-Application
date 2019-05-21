package com.example.movieapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.VolleyError;
import com.example.movieapp.models.BookedSeat;
import com.example.movieapp.models.Calendar;
import com.example.movieapp.models.SeatMo;
import com.example.movieapp.models.ShowMatch;
import com.example.movieapp.models.Sport;
import com.example.movieapp.models.Stadium;
import com.example.movieapp.models.Ticket;
import com.example.movieapp.utils.AppManager;
import com.example.movieapp.utils.Constant;
import com.example.movieapp.utils.DataParser;
import com.example.movieapp.utils.Utilities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SeatPlaceSportActivity extends SeatPlaceActivity {

    private Sport sport;
    private Stadium stadium;
    private ShowMatch showMatch;
    private static final String TAG_SEAT_SPORT = "TAG_SEAT_SPORT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getDataFromPreviousActivity() {
        String sportJson = getIntent().getStringExtra("sport");
        String showmatchJson = getIntent().getStringExtra("showmatch");
        String stadiumJson = getIntent().getStringExtra("stadium");
        String calendarJson = getIntent().getStringExtra("calendar");
        if (!sportJson.isEmpty()) {
            sport = gson.fromJson(sportJson, Sport.class);
        }
        if (!calendarJson.isEmpty()) {
            calendar = gson.fromJson(calendarJson, Calendar.class);
        }
        if (!showmatchJson.isEmpty()) {
            showMatch = gson.fromJson(showmatchJson, ShowMatch.class);
        }
        if (!stadiumJson.isEmpty()) {
            stadium = gson.fromJson(stadiumJson, Stadium.class);
        }
        map = (Map<Ticket, Integer>) getIntent().getSerializableExtra("boughtTicketMap");
    }

    @Override
    protected void setDataForEvent() {
        txtCinema.setText(stadium.getName());
        String place = showMatch.getLocation();
        txtBranch.setText(" - " + place);
        txtTime.setText(" - " + showMatch.getTimeStart());
    }

    private boolean isVipPosition(int i, int j) {
        return (i >= Constant.SeatTypePosition.NORMAL && i < Constant.SeatTypePosition.VIP
                && j >= 3 && j <= 8);
    }

    @Override
    protected void setupTimer() {
        new CountDownTimer(300000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtCurrentTime.setText(new SimpleDateFormat("mm:ss").format(new Date(millisUntilFinished)));
                currentTime = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                showDialogErrorWithOKButtonListener(SeatPlaceSportActivity.this, "Thông báo", "Đã hết thời gian đặt vé. Vui lòng thử lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SeatPlaceSportActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                // Toast.makeText(SeatPlaceActivity.this, "Hết thời gian đặt vé", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    @Override
    protected void sendDataToNextActivity() {
        if (selectedSeats.size() == 0) {
            showDialogErrorWithOKButton(SeatPlaceSportActivity.this, "Lỗi", "Vui lòng chọn ghế để tiến hành thanh toán");
            return;
        }
        Intent intent = new Intent(SeatPlaceSportActivity.this, ConfirmationSportActivity.class);
        String jsonSport = gson.toJson(sport);
        String jsonShowmatch = gson.toJson(showMatch);
        String jsonStadium = gson.toJson(stadium);
        String jsonCalendar = gson.toJson(calendar);

        intent.putExtra("sport", jsonSport);
        intent.putExtra("boughtTicketMap", (Serializable) map);
        intent.putExtra("stadium", jsonStadium);
        intent.putExtra("showmatch", jsonShowmatch);
        intent.putExtra("calendar", jsonCalendar);
        intent.putExtra("timer", currentTime);
        intent.putExtra("selectedSeat", (Serializable) selectedSeats);
        startActivity(intent);
    }

    @Override
    protected SeatMo configSpecifySeatForPosition(int i, int j, SeatMo seat) {
        if (isVipPosition(i, j) && isVip) {
            seat.status = 1;
            seat.setPrice(150000);
            seat.setTypeSeat(Constant.SeatTypePosition.VIP);
        }
        if (!isVipPosition(i, j) && isNormal) {
            seat.status = 1;
            seat.setPrice(100000);
            seat.setTypeSeat(Constant.SeatTypePosition.NORMAL);
        }
        return seat;
    }

    @Override
    protected int getTypeOfSeatOnClick(int row, int column) {
        int type = 0;
        if (row >= Constant.SeatTypePosition.NORMAL && row < Constant.SeatTypePosition.VIP
                && column >= 3 && column <= 8 && isVip) {
            type = Constant.SeatType.VIP;
        } else {
            type = Constant.SeatType.NORMAL;
        }
        return type;
    }

    @Override
    protected void removeSeatAfterClickAtPosition(int row, int column) {
        // do nothing
    }

    @Override
    protected void getBookedSeatOfEvent() {
        // TODO: implement later
        AppManager.getInstance().getCommService().getBookedSeatByEvent(TAG_SEAT_SPORT, showMatch.getId(), stadium.getId(),
                new DataParser.DataResponseListener<LinkedList<BookedSeat>>() {
            @Override
            public void onDataResponse(LinkedList<BookedSeat> result) {
                bookedSeats = result;
                for (BookedSeat bookedSeat : bookedSeats) {
                    String blockName = bookedSeat.getRow();
                    int actualRow = Utilities.convertStringToInt(blockName) - 1;
                    int col = Integer.parseInt(bookedSeat.getNumber()) - 1;
                    SeatMo seatMo = seatTable[actualRow][col];
                    if (seatMo == null) continue;
                    seatMo.setId(bookedSeat.getId());
                    if (bookedSeat.getStatus() == -1)
                        seatMo.status = bookedSeat.getStatus();
                }
            }

            @Override
            public void onDataError(String errorMessage) {

            }

            @Override
            public void onRequestError(String errorMessage, VolleyError volleyError) {

            }

            @Override
            public void onCancel() {

            }
        });

    }
}
