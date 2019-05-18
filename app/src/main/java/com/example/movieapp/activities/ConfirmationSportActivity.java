package com.example.movieapp.activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.movieapp.R;
import com.example.movieapp.adapters.PaymentMethodAdapter;
import com.example.movieapp.fragments.CardInfoFragment;
import com.example.movieapp.models.Calendar;
import com.example.movieapp.models.SeatMo;
import com.example.movieapp.models.ShowMatch;
import com.example.movieapp.models.Sport;
import com.example.movieapp.models.Stadium;
import com.example.movieapp.models.Ticket;
import com.example.movieapp.utils.Constant;
import com.example.movieapp.utils.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ConfirmationSportActivity extends ConfirmationActivity implements PaymentMethodAdapter.PaymentMethodListener, CardInfoFragment.CardInfoListener {

    private Stadium stadium;
    private Sport sport;
    private ShowMatch showMatch;

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
        currentTime = getIntent().getLongExtra("timer", 0);
        selectedSeats = (List<SeatMo>) getIntent().getSerializableExtra("selectedSeat");
        map = (Map<Ticket, Integer>) getIntent().getSerializableExtra("boughtTicketMap");
    }

    @Override
    public void onMethodSelected(int pos) {
        super.onMethodSelected(pos);
    }

    @Override
    protected void setCardInfoForEvent() {
        cardInfoFragment.setEventType(Constant.EventType.SPORT);
        cardInfoFragment.setListener(this);
        showFragmentWithCustomAnimation(cardInfoFragment, R.id.container_fragment, R.anim.slide_in_up, R.anim.slide_out_up);
    }

    @Override
    protected void setupUIData() {
        txtCinema.setText(stadium.getName());
        String place = showMatch.getLocation();
        txtBranch.setText(" - " + place);
        txtDate.setText(calendar.getName());
        txtTime.setText(" - " + showMatch.getTimeStart());
        txtRoomNumber.setVisibility(View.GONE);
        txtCurrentTime.setTextColor(getResources().getColor(R.color.colorPrimary));

        new CountDownTimer(currentTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtCurrentTime.setText(new SimpleDateFormat("mm:ss").format(new Date(millisUntilFinished)));
                currentTime = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                showDialogErrorWithOKButtonListener(ConfirmationSportActivity.this, "Thông báo", "Đã hết thời gian đặt vé. Vui lòng thử lại", (dialog, which) -> {
                    Intent intent = new Intent(ConfirmationSportActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });
                // Toast.makeText(ConfirmationActivity.this, "Hết thời gian đặt vé", Toast.LENGTH_SHORT).show();
            }
        }.start();

        double totalPrice = 0;
        for (Map.Entry<Ticket, Integer> entry : map.entrySet()) {
            Ticket ticket = entry.getKey();
            int quantity = entry.getValue();
            totalPrice += ticket.getPrice() * quantity;
        }
        txtTotalPrice.setText(Utilities.formatCurrency(totalPrice));
        imgClose.setOnClickListener(v -> showDialogErrorWithOKButtonListener(ConfirmationSportActivity.this,
                "Thông báo",
                "Bạn có chắc chắn muốn hủy giao dịch này?",
                (dialog, which) -> finish()));

        txtTitle.setText(sport.getName());
        txtMinAge.setText(showMatch.getTimeStart());
        txtDescription.setText(sport.getDescription());

        String seatPos = "Vị trí ghế: ";
        for (SeatMo seat : selectedSeats) {
            seatPos += seat.getSeatName();
        }
        txtSeatPlaces.setText(seatPos);
    }

    @Override
    public void onFinishInputCardHolder(String name, String cardNumber, String expirationDate) {
        super.onFinishInputCardHolder(name, cardNumber, expirationDate);
    }

    public Stadium getStadium() {
        return stadium;
    }

    public Sport getSport() {
        return sport;
    }

    public ShowMatch getShowMatch() {
        return showMatch;
    }

    @Override
    protected void proceedCreateTicketAction() {
        if (cardNumber == null || cardNumber.isEmpty()) {
            showDialogErrorWithOKButton(ConfirmationSportActivity.this, "Thông báo", "Vui lòng chọn phương thức thanh toán để tiếp tục");
            return;
        }

        JSONObject jsonObject = prepareBodyRequest(cardNumber);
        createTicket(jsonObject);
    }

    @Override
    protected JSONObject prepareBodyRequest(String cardNumber) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("account_id", 1);
            jsonObject.put("sale_id", showMatch.getId());
            jsonObject.put("type", Constant.TicketType.EVENT);
            jsonObject.put("card_number", cardNumber);
            JSONArray array = new JSONArray();
            for (SeatMo seat : selectedSeats) {
                JSONObject jsonObjChild = new JSONObject();
                jsonObjChild.put("seat_id", seat.getId());
                jsonObjChild.put("price", seat.getPrice());
                array.put(jsonObjChild);
            }
            jsonObject.put("bookedSeats", array);
            array = new JSONArray();
            jsonObject.put("bookedCombos", array);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
