package com.example.movieapp.activities;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.movieapp.R;
import com.example.movieapp.models.BookedSeat;
import com.example.movieapp.models.Ticket;
import com.example.movieapp.models.TicketInfo;
import com.example.movieapp.utils.AppManager;
import com.example.movieapp.utils.DataParser;

import net.glxn.qrgen.android.QRCode;

public class ResultTicketActivity extends BaseActivity {

    private ImageView imgQRCode;
    private int userId;
    private int ticketId;
    private TicketInfo ticketInfo;
    private static final String TAG_GET_TICKET = "TAG_GET_TICKET";

    private TextView txtTitle, txtMinAge, txtDescription,
            txtSeatPlaces, txtCode, txtType,
            txtDate, txtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_ticket);
        imgQRCode = findViewById(R.id.imgQRCode);
        txtTitle = findViewById(R.id.txtTitle);
        txtMinAge = findViewById(R.id.txtMinAge);
        txtDescription = findViewById(R.id.txtDescription);
        txtSeatPlaces = findViewById(R.id.txtSeatPlaces);
        txtType = findViewById(R.id.txtType);
        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        txtCode = findViewById(R.id.txtCode);

        getDataFromPreviousActivity();
        getTicket();
    }

    private void initialGUI() {
        txtTitle.setText(ticketInfo.getMovieName());
        txtMinAge.setText(ticketInfo.getMinAge());
        txtDescription.setText(ticketInfo.getRunningTime() + " - " + ticketInfo.getType());
        txtType.setText(ticketInfo.getCinemaName() + " - " + ticketInfo.getRoom());
        txtDate.setText(ticketInfo.getReleaseDateAsString());
        txtTime.setText(ticketInfo.getTimeStr());
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
