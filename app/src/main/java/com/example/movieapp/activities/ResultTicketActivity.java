package com.example.movieapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.movieapp.R;
import com.example.movieapp.models.BookedSeat;
import com.example.movieapp.models.TicketInfo;
import com.example.movieapp.utils.AppManager;
import com.example.movieapp.utils.DataParser;
import com.squareup.picasso.Picasso;

import net.glxn.qrgen.android.QRCode;

public class ResultTicketActivity extends BaseActivity {

    protected ImageView imgQRCode, cinemaIcon;
    private int userId;
    private int ticketId;
    protected TicketInfo ticketInfo;
    private static final String TAG_GET_TICKET = "TAG_GET_TICKET";

    protected TextView txtTitle, txtMinAge, txtDescription,
            txtAddress,  txtCinema,
            txtSeatPlaces, txtCode,
            txtDate, txtTime, txtRoom, txtHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_ticket);
        imgQRCode = findViewById(R.id.imgQRCode);
        cinemaIcon = findViewById(R.id.cinemaIcon);
        txtTitle = findViewById(R.id.txtTitle);
        txtMinAge = findViewById(R.id.txtMinAge);
        txtCinema = findViewById(R.id.txtCinema);
        txtAddress = findViewById(R.id.txtAddress);
        txtDescription = findViewById(R.id.txtDescription);
        txtSeatPlaces = findViewById(R.id.txtSeatPlaces);
        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        txtRoom = findViewById(R.id.txtRoom);
        txtCode = findViewById(R.id.txtCode);
        txtHome = findViewById(R.id.txtHome);

        getDataFromPreviousActivity();
        getTicket();

        txtHome.setOnClickListener(v -> {
            Intent intent = new Intent(ResultTicketActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    protected void initialGUI() {
        txtTitle.setText(ticketInfo.getMovieName());
        txtMinAge.setText("C" + ticketInfo.getMinAge());
        txtDescription.setText(ticketInfo.getRunningTime() + " - " + ticketInfo.getGenre());
        txtCinema.setText(ticketInfo.getCinemaName());
        txtAddress.setText(ticketInfo.getAddress());
        Picasso.get().load(ticketInfo.getIconURL()).error(R.drawable.purchased_tickets).into(cinemaIcon);
        txtDate.setText(ticketInfo.getReleaseDateAsString());
        txtTime.setText(ticketInfo.getTimeStr());
        txtRoom.setText(ticketInfo.getRoom());
        txtCode.setText(ticketInfo.getCode());
        String seats = "";
        for (BookedSeat bookedSeat : ticketInfo.getBookedSeats()) {
            seats += bookedSeat.getRow() + bookedSeat.getNumber() + " ";
        }
        txtSeatPlaces.setText(seats);
    }

    private void getDataFromPreviousActivity() {
        userId = getIntent().getIntExtra("user_id", 0);
        ticketId = getIntent().getIntExtra("ticket_id", 0);
    }

    private void getTicket() {
        AppManager.getInstance().getCommService().getTicketInfoOfUser(TAG_GET_TICKET, ticketId, userId, new DataParser.DataResponseListener<TicketInfo>() {
            @Override
            public void onDataResponse(TicketInfo result) {
                ticketInfo = result;
                prepareQRCode();
                initialGUI();
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

    private void prepareQRCode() {
        Bitmap bitmap = QRCode.from(ticketInfo.getCode()).bitmap();
        imgQRCode.setImageBitmap(bitmap);
    }
}
