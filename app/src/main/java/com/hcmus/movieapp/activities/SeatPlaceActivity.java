package com.hcmus.movieapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.models.BookedSeat;
import com.hcmus.movieapp.models.Calendar;
import com.hcmus.movieapp.models.Cinema;
import com.hcmus.movieapp.models.Movie;
import com.hcmus.movieapp.models.SeatMo;
import com.hcmus.movieapp.models.Showtime;
import com.hcmus.movieapp.models.Ticket;
import com.hcmus.movieapp.utils.AppManager;
import com.hcmus.movieapp.utils.Constant;
import com.hcmus.movieapp.utils.DataParser;
import com.hcmus.movieapp.utils.Utilities;
import com.github.captain_miao.seatview.BaseSeatMo;
import com.github.captain_miao.seatview.MovieSeatView;
import com.github.captain_miao.seatview.SeatPresenter;
import com.google.gson.Gson;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SeatPlaceActivity extends BaseActivity implements SeatPresenter {

    private static final String TAG = "SelectMovieSeat";
    private static final int MAX_SEATS = 5;

    protected MovieSeatView mMovieSeatView;
    protected SeatMo[][] seatTable;

    public List<SeatMo> selectedSeats;
    protected TextView txtTotalPrice, txtContinue;
    protected TextView  txtCinema, txtBranch, txtDate,
            txtTime, txtRoomNumber, txtCurrentTime;
    protected ImageView imgClose;

    protected int maxRow = 10;
    protected int maxColumn = 12;
    private static final String TAG_BOOKED_SEAT = "TAG_BOOKED_SEAT";
    protected List<BookedSeat> bookedSeats = new ArrayList<>();

    private Cinema cinema;
    protected Calendar calendar;
    private Movie movie;
    private Showtime showtime;
    private CountDownTimer countDownTimer;

    protected Map<Ticket, Integer> map = new HashMap<>();
    protected Gson gson = new Gson();
    protected boolean isNormal, isVip, isCouple;
    protected long currentTime;

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
        getBookedSeatOfEvent();
        setupUIData();

        txtContinue.setOnClickListener(v -> sendDataToNextActivity());
    }

    protected void sendDataToNextActivity() {
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
    }

    protected void getDataFromPreviousActivity() {
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



    protected void setDataForEvent() {
        txtCinema.setText(cinema.getName());
        String roomName = showtime.getBranchName().substring(showtime.getBranchName().lastIndexOf(" ") + 1);
        txtBranch.setText(" - " + roomName);
        txtTime.setText(" - " + showtime.getTimeStart());
    }

    protected void setupTimer() {
        countDownTimer = new CountDownTimer(300000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtCurrentTime.setText(new SimpleDateFormat("mm:ss").format(new Date(millisUntilFinished)));
                currentTime = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                countDownTimer.start();
                selectedSeats.clear();
                getBookedSeatOfEvent();
                // Toast.makeText(SeatPlaceActivity.this, "Hết thời gian đặt vé", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    protected void setupPreSelectedSeat(Context context) {
        for (Map.Entry<Ticket, Integer> entry : map.entrySet()) {
            Ticket ticket = entry.getKey();
            int maxQuantity = entry.getValue();
            List<Integer> positionRows = getRowOfSeatByType(ticket.getId());
            int minRow = positionRows.get(0);
            int maxRow = positionRows.get(1);
            int selectedSuccessfully = 0;
            int increaseNumb = 1;
            int decreaseNumb = 0;
            if (ticket.getId() == Constant.SeatType.COUPLE) {
                increaseNumb = 2;
                decreaseNumb = 1;
            }
            for (int i = 0; i < maxQuantity; i++) {
                for (int j = minRow; j <= maxRow; j++) {
                    for (int k = 0; k < maxColumn - decreaseNumb; k += increaseNumb) {
                        SeatMo seatMo = seatTable[j][k];
                        if (seatMo != null && seatMo.isOnSale()) {
                            if (selectedSuccessfully == maxQuantity) {
                                break;
                            }
                            onClickSeat(j, k, seatMo);
                            selectedSuccessfully++;

                        }
                    }
                }
            }
            if (selectedSuccessfully < maxQuantity) {
                showDialogErrorWithOKButtonListener(context, "Thông báo",
                        "Suất chiếu không còn đủ vé. Vui lòng chọn suất chiếu khác", (dialog, which) -> {
                            Intent intent = new Intent(context, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        });
            }

        }
        mMovieSeatView.invalidate();
    }

    private List<Integer> getRowOfSeatByType(int type) {
        List<Integer> positions = new ArrayList<>();
        int min = 0; // first row
        int max = maxRow - 1; // last row
        switch (type) {
            case Constant.SeatType.NORMAL:
                min = 0;
                max = Constant.SeatTypePosition.NORMAL - 1;
                break;
            case Constant.SeatType.VIP:
                min = Constant.SeatTypePosition.NORMAL;
                max = Constant.SeatTypePosition.VIP - 1;
                break;
            case Constant.SeatType.COUPLE:
                min = Constant.SeatTypePosition.VIP;
                max = Constant.SeatTypePosition.COUPLE - 1;
                break;
        }
        positions.add(min);
        positions.add(max);
        return positions;
    }

    protected void setupUIData() {
        setDataForEvent();
        txtDate.setText(calendar.getName());
        txtRoomNumber.setVisibility(View.GONE);
        txtCurrentTime.setTextColor(getResources().getColor(R.color.colorPrimary));

        setupTimer();

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
                (dialog, which) -> {
                    Intent intent = new Intent(SeatPlaceActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }));
    }

    protected void getBookedSeatOfEvent() {
        AppManager.getInstance().getCommService().getBookedSeatByMovie(TAG_BOOKED_SEAT, showtime.getId(), showtime.getLocation_id(), new DataParser.DataResponseListener<LinkedList<BookedSeat>>() {
            @Override
            public void onDataResponse(LinkedList<BookedSeat> result) {
                bookedSeats = result;
                List<Integer> seatTypes = getSeatTypes();
                for (BookedSeat bookedSeat : bookedSeats) {
                    String blockName = bookedSeat.getRow();
                    int actualRow = Utilities.convertStringToInt(blockName) - 1;
                    int col = Integer.parseInt(bookedSeat.getNumber()) - 1;
                    SeatMo seatMo = seatTable[actualRow][col];
                    if (seatMo == null) continue;
                    seatMo.setId(bookedSeat.getId());
                    if (bookedSeat.getStatus() != -1 && seatTypes.contains(seatMo.getTypeSeat())) {
                        seatMo.status = bookedSeat.getStatus();
                    }
                }
                mMovieSeatView.invalidate();

                setupPreSelectedSeat(SeatPlaceActivity.this);
            }

            @Override
            public void onDataError(String errorMessage) {
                bookedSeats = new ArrayList<>();
            }

            @Override
            public void onRequestError(String errorMessage, VolleyError volleyError) {

            }

            @Override
            public void onCancel() {

            }
        });
    }

    protected void initSeatTable() {
        seatTable = new SeatMo[maxRow][maxColumn];
        enableSeatPos();
        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxColumn; j++) {
                SeatMo seat = new SeatMo();
                seat.row = i;
                seat.column = j;
                seat.rowName = String.valueOf((char)('A' + i));
                seat.seatName = seat.rowName + (j + 1);
                seat.status = -1;

                seat = configSpecifySeatForPosition(i, j, seat);
                seatTable[i][j] = seat;
            }
        }
    }

    protected SeatMo configSpecifySeatForPosition(int i, int j, SeatMo seat) {
        if (i < Constant.SeatTypePosition.NORMAL && isNormal) {
            seat.status = 1;
            seat.setPrice(90000);
            seat.setTypeSeat(Constant.SeatType.NORMAL);
        }
        if (i >= Constant.SeatTypePosition.NORMAL && i < Constant.SeatTypePosition.VIP && isVip) {
            seat.status = 1;
            seat.setPrice(100000);
            seat.setTypeSeat(Constant.SeatType.VIP);
        }
        if (i >= Constant.SeatTypePosition.VIP && i < Constant.SeatTypePosition.COUPLE && isCouple) {
            seat.status = 1;
            seat.setPrice(120000);
            seat.setTypeSeat(Constant.SeatType.COUPLE);
        }

        if ((j == 3 || j == 8) && i < 8) {
            seat = null;
        }
        return seat;
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

    protected void enableSeatPos() {
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

    protected List<Integer> getSeatTypes() {
        List<Integer> types = new ArrayList<>();
        for (Ticket ticket : map.keySet()) {
            types.add(ticket.getId());
        }
        return types;
    }

    protected String getTypeName(int type) {
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

    protected int getTotalSeatCanSelect() {
        int sum = 0;
        for (int i : map.values()) {
            sum += i;
        }
        return sum;
    }

    protected int getTotalSelectedSeatByType(int type) {
        int sum = 0;
        for (SeatMo seatMo : selectedSeats) {
            if (seatMo.getTypeSeat() == type) {
                sum++;
            }
        }
        return sum;
    }

    private SeatMo findPairSeatWithSeatHasColumn(int row, int column) {
        SeatMo pairSeat = null;
        if (column % 2 == 0) {
            pairSeat = seatTable[row][column + 1];
        } else {
            pairSeat = seatTable[row][column - 1];
        }
        return pairSeat;
    }

    protected int getTypeOfSeatOnClick(int row, int column) {
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
        return type;
    }

    protected void removeSeatAfterClickAtPosition(int row, int column) {
        SeatMo pairSeat = null;
        if (row >= Constant.SeatTypePosition.VIP && row < Constant.SeatTypePosition.COUPLE && isCouple) {
            pairSeat = findPairSeatWithSeatHasColumn(row, column);
            pairSeat.setOnSale();
            selectedSeats.remove(pairSeat);
        }
    }

    @Override
    public boolean onClickSeat(int row, int column, BaseSeatMo seat) {
        SeatMo seatMo = seatTable[row][column];

        if(seatMo != null){
            if (seatMo.isOnSale()) {
                int max_seats = isCouple ? getTotalSeatCanSelect() * 2 : getTotalSeatCanSelect();
                int type = getTypeOfSeatOnClick(row, column);

                Map.Entry<Ticket, Integer> entry = getTicketByType(type);
                int maxSeatByType = isCouple ? entry.getValue() * 2 : entry.getValue();
                int totalSelectedSeatByType = getTotalSelectedSeatByType(seatMo.getTypeSeat());
                if (selectedSeats.size() < max_seats && totalSelectedSeatByType < maxSeatByType) {
                    seatMo.setSelected();
                    selectedSeats.add(seatMo);
                    if (type == Constant.SeatType.COUPLE) {
                        SeatMo pairSeat = findPairSeatWithSeatHasColumn(row, column);
                        pairSeat.setSelected();
                        selectedSeats.add(pairSeat);
                    }
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
                removeSeatAfterClickAtPosition(row, column);
                return true;
            }
        }
        return false;
    }
}
