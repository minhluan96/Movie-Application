package com.example.movieapp.activities;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.movieapp.R;
import com.example.movieapp.models.SeatMo;
import com.github.captain_miao.seatview.BaseSeatMo;
import com.github.captain_miao.seatview.MovieSeatView;
import com.github.captain_miao.seatview.SeatPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SeatPlaceActivity extends BaseActivity implements SeatPresenter {

    private static final String TAG = "SelectMovieSeat";
    private static final int MAX_SEATS = 5;

    private MovieSeatView mMovieSeatView;
    private SeatMo[][] seatTable;

    public List<SeatMo> selectedSeats;
    private GestureDetector gestureDetector;


    private int maxRow = 10;
    private int maxColumn = 12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_place);

        initSeatTable();
        selectedSeats = new ArrayList<>();
        mMovieSeatView = findViewById(R.id.seat_view);
        mMovieSeatView.setSeatTable(seatTable);
        mMovieSeatView.setPresenter(this);
        gestureDetector = new GestureDetector(this, new GestureListener());


    }


    private void initSeatTable() {
        seatTable = new SeatMo[maxRow][maxColumn];// mock data
        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxColumn; j++) {
                SeatMo seat = new SeatMo();
                seat.row = i;
                seat.column = j;
                seat.rowName = String.valueOf((char)('A' + i));
                seat.seatName = seat.rowName + " Row" + (j + 1) + " Seat";
//                seat.status = 1;
                seat.status = randInt(-2, 1);
                seatTable[i][j] = seat.status == -2 ? null : seat;
            }
        }
    }

    public  int randInt(int min, int max) {

        Random rand = new Random();

        return rand.nextInt((max - min) + 1) + min;
    }


    @Override
    public boolean onClickSeat(int row, int column, BaseSeatMo seat) {
        SeatMo seatMo = seatTable[row][column];

        if(seatMo != null){
            if (seatMo.isOnSale()) {
                if (selectedSeats.size() < MAX_SEATS) {
                    seatMo.setSelected();
                    selectedSeats.add(seatMo);
                    return true;
                } else {
                    Toast.makeText(this, getString(R.string.seat_max_number, MAX_SEATS), Toast.LENGTH_SHORT).show();
                }

            } else if (seatMo.isSelected()) {
                seatMo.setOnSale();
                selectedSeats.remove(seatMo);
                return true;
            }
        }
        return false;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (mMovieSeatView.mScaleFactor > 1) {
                mMovieSeatView.mScaleFactor = 0.5f;
            }
            return true;
        }
    }
}
