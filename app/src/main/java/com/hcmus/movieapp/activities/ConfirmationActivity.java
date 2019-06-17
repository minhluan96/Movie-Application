package com.hcmus.movieapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.adapters.PaymentMethodAdapter;
import com.hcmus.movieapp.fragments.CardInfoFragment;
import com.hcmus.movieapp.models.Account;
import com.hcmus.movieapp.models.Calendar;
import com.hcmus.movieapp.models.Cinema;
import com.hcmus.movieapp.models.Movie;
import com.hcmus.movieapp.models.PaymentMethod;
import com.hcmus.movieapp.models.SeatMo;
import com.hcmus.movieapp.models.Showtime;
import com.hcmus.movieapp.models.Ticket;
import com.hcmus.movieapp.utils.AppManager;
import com.hcmus.movieapp.utils.Constant;
import com.hcmus.movieapp.utils.DataParser;
import com.hcmus.movieapp.utils.SaveSharedPreference;
import com.hcmus.movieapp.utils.Utilities;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfirmationActivity extends BaseActivity implements PaymentMethodAdapter.PaymentMethodListener, CardInfoFragment.CardInfoListener {

    protected RecyclerView rvPaymentMethods;
    protected PaymentMethodAdapter paymentMethodAdapter;
    protected RecyclerView.LayoutManager layoutManager;
    protected TextView txtContinue, txtTotalPrice;
    protected TextView txtCinema, txtBranch, txtDate,
            txtTime, txtRoomNumber, txtCurrentTime;

    protected TextView txtTitle, txtMinAge, txtDescription, txtSeatPlaces;
    protected ImageView imgClose;

    protected Gson gson = new Gson();
    protected Calendar calendar;
    private Movie movie;
    private Showtime showtime;
    private Cinema cinema;
    protected Map<Ticket, Integer> map = new HashMap<>();
    protected List<SeatMo> selectedSeats = new ArrayList<>();
    protected long currentTime;
    protected String cardNumber;
    private static final String TAG_CREATE_TICKET = "TAG_CREATE_TICKET";

    protected CardInfoFragment cardInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        rvPaymentMethods = findViewById(R.id.rv_paymentMethod);

        layoutManager = new GridLayoutManager(this, 3);
        rvPaymentMethods.setLayoutManager(layoutManager);

        txtContinue = findViewById(R.id.txtContinue);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        txtCinema = findViewById(R.id.txtCinema);
        txtBranch = findViewById(R.id.txtBranch);
        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        txtRoomNumber = findViewById(R.id.txtRoomNumber);
        txtCurrentTime = findViewById(R.id.txtCurrentTime);
        imgClose = findViewById(R.id.imgClose);
        txtTitle = findViewById(R.id.txtTitle);
        txtMinAge = findViewById(R.id.txtMinAge);
        txtDescription = findViewById(R.id.txtDescription);
        txtSeatPlaces = findViewById(R.id.txtSeatPlaces);

        txtContinue.setOnClickListener(v -> proceedCreateTicketAction());

        getDataFromPreviousActivity();
        setupUIData();
        setupPaymentMethodAdapter();
    }

    protected void proceedCreateTicketAction() {
        if (cardNumber == null || cardNumber.isEmpty()) {
            showDialogErrorWithOKButton(ConfirmationActivity.this, "Thông báo", "Vui lòng chọn phương thức thanh toán để tiếp tục");
            return;
        }

        JSONObject jsonObject = prepareBodyRequest(cardNumber);
        createTicket(jsonObject);
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
        currentTime = getIntent().getLongExtra("timer", 0);
        selectedSeats = (List<SeatMo>) getIntent().getSerializableExtra("selectedSeat");
        map = (Map<Ticket, Integer>) getIntent().getSerializableExtra("boughtTicketMap");
    }

    protected void setupUIData() {
        txtCinema.setText(cinema.getName());
        String roomName = showtime.getBranchName().substring(showtime.getBranchName().lastIndexOf(" ") + 1);
        txtBranch.setText(" - " + roomName);
        txtDate.setText(calendar.getName());
        txtTime.setText(" - " + showtime.getTimeStart());
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
                showDialogErrorWithOKButtonListener(ConfirmationActivity.this, "Thông báo", "Đã hết thời gian đặt vé. Vui lòng thử lại", (dialog, which) -> {
                    Intent intent = new Intent(ConfirmationActivity.this, HomeActivity.class);
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
        imgClose.setOnClickListener(v -> showDialogErrorWithOKButtonListener(ConfirmationActivity.this,
                "Thông báo",
                "Bạn có chắc chắn muốn hủy giao dịch này?",
                (dialog, which) -> {
                    Intent intent = new Intent(ConfirmationActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }));

        txtTitle.setText(movie.getName());
        txtMinAge.setText(movie.minAgeDisplay());
        txtDescription.setText(movie.getLength() + " - " + showtime.getTypeMovie());

        String seatPos = "Vị trí ghế: ";
        for (SeatMo seat : selectedSeats) {
            seatPos += seat.getSeatName();
        }
        txtSeatPlaces.setText(seatPos);
    }

    private void setupPaymentMethodAdapter() {
        List<PaymentMethod> paymentMethods = setDummyDataPaymentMethod();
        paymentMethodAdapter = new PaymentMethodAdapter(paymentMethods, this);
        rvPaymentMethods.setAdapter(paymentMethodAdapter);
        paymentMethodAdapter.setListener(this);
    }

    private List<PaymentMethod> setDummyDataPaymentMethod() {
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        paymentMethods.add(new PaymentMethod(1, "Momo", "https://avatars3.githubusercontent.com/u/36770798?s=400&v=4"));
        paymentMethods.add(new PaymentMethod(2, "Visa/Master", ""));
        paymentMethods.add(new PaymentMethod(3, "Thẻ ATM nội địa", ""));
        return paymentMethods;
    }

    @Override
    public void onMethodSelected(int pos) {
        if (pos == 0) {
            Toast.makeText(getApplicationContext(), "Phương thức thanh toán sẽ được hỗ trợ trong tương lai", Toast.LENGTH_SHORT).show();
            return;
        }
        cardInfoFragment = new CardInfoFragment();
        cardInfoFragment.setType(pos - 1); // due to the type is only has 2 values [0, 1]
        setCardInfoForEvent();
    }

    protected void setCardInfoForEvent() {
        cardInfoFragment.setEventType(Constant.EventType.MOVIE);
        cardInfoFragment.setListener(this);
        showFragmentWithCustomAnimation(cardInfoFragment, R.id.container_fragment, R.anim.slide_in_up, R.anim.slide_out_up);
    }

    public void removeCardInfoFragment() {
        hideFragment(cardInfoFragment);
    }

    @Override
    public void onBackPressed() {
        if (cardInfoFragment != null && cardInfoFragment.isVisible()) {
            if (cardInfoFragment.isBankListDisplay()) {
                hideFragment(cardInfoFragment);
            } else {
                cardInfoFragment.showCardInfoContainer(false);
            }
        } else {
            super.onBackPressed();
        }
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public Movie getMovie() {
        return movie;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public Map<Ticket, Integer> getMap() {
        return map;
    }

    public List<SeatMo> getSelectedSeats() {
        return selectedSeats;
    }

    @Override
    public void onFinishInputCardHolder(String name, String cardNumber, String expirationDate) {
        this.cardNumber = cardNumber;
        removeCardInfoFragment();
    }

    protected JSONObject prepareBodyRequest(String cardNumber) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("account_id", 1);
            jsonObject.put("movie_showings_id", showtime.getId());
            jsonObject.put("location_id", showtime.getLocation_id());
            jsonObject.put("type", Constant.TicketType.MOVIE);
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

    protected Intent getNextIntent() {
        return new Intent(ConfirmationActivity.this, ResultTicketActivity.class);
    }

    protected void createTicket(JSONObject body) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(Html.fromHtml("<font color='#323232'>"  + "Hệ thống đang xử lý" +"</font>"));
        dialog.show();
        AppManager.getInstance().getCommService().createMovieTicket(TAG_CREATE_TICKET, body, new DataParser.DataResponseListener<Ticket>() {
            @Override
            public void onDataResponse(Ticket result) {
                Ticket ticket = result;
                cardNumber = "";
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Intent intent = getNextIntent();
                // TODO: using the real user id instead of the fake data
                intent.putExtra("user_id", 1);
                intent.putExtra("ticket_id", ticket.getId());
                startActivity(intent);
            }

            @Override
            public void onDataError(String errorMessage) {
                String error = errorMessage;
                cardNumber = null;
                dialog.hide();

                // TODO: Be carefully with this context
                Toast.makeText(ConfirmationActivity.this, "Thanh toán thất bại. Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestError(String errorMessage, VolleyError volleyError) {
                cardNumber = null;
                dialog.hide();
                Toast.makeText(ConfirmationActivity.this, "Thanh toán thất bại. Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                cardNumber = null;
            }
        });
    }
}
