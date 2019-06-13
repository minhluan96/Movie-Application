package com.hcmus.movieapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hcmus.movieapp.utils.FileUtils;
import com.hcmus.movieapp.utils.Utilities;
import com.squareup.picasso.Picasso;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.models.BookedSeat;
import com.hcmus.movieapp.models.TicketInfo;
import com.hcmus.movieapp.utils.AppManager;
import com.hcmus.movieapp.utils.DataParser;

import net.glxn.qrgen.android.QRCode;

import java.io.File;
import java.text.SimpleDateFormat;

public class ResultTicketActivity extends BaseActivity {

    protected ImageView imgQRCode, cinemaIcon;
    private int userId;
    private int ticketId;
    protected TicketInfo ticketInfo;
    private static final String TAG_GET_TICKET = "TAG_GET_TICKET";
    private View containerTicketView;

    protected TextView txtTitle, txtMinAge, txtDescription,
            txtAddress,  txtCinema,
            txtSeatPlaces, txtCode,
            txtDate, txtTime, txtRoom,
            txtHome, txtSave, txtShare;

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
        txtSave = findViewById(R.id.txtSave);
        txtShare = findViewById(R.id.txtShare);
        containerTicketView = findViewById(R.id.container_ticket_view);

        getDataFromPreviousActivity();
        getTicket();

        txtHome.setOnClickListener(v -> {
            Intent intent = new Intent(ResultTicketActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        txtSave.setOnClickListener(v -> saveTicketAsImage());

        txtShare.setOnClickListener(v -> {
            saveTicketAndShare();
        });
    }

    private void saveTicketAndShare() {
        Bitmap bitmap = Utilities.getBitMapFromView(containerTicketView);
        Uri uri = FileUtils.saveImageAndGetUri(bitmap, ResultTicketActivity.this);
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/png");
        startActivity(intent);
    }


    private void saveTicketAsImage() {
        Bitmap bitmap = Utilities.getBitMapFromView(containerTicketView);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(System.currentTimeMillis());
        File storageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                        + "/Tickets/");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        String fileStr = "ticket-" + timeStamp + ".jpg";
        File file = new File(storageDir, fileStr);
        FileUtils.saveBitmap(bitmap, file);
        Toast.makeText(ResultTicketActivity.this,
                "Vé đã được lưu tại " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
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
