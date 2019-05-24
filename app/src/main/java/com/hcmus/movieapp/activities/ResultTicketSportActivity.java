package com.hcmus.movieapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.movieapp.R;
import com.squareup.picasso.Picasso;

import net.glxn.qrgen.android.QRCode;

public class ResultTicketSportActivity extends ResultTicketActivity {

    private ImageView unitIcon;
    private TextView txtCategory, txtOrganizer, txtVenue, txtGateway, txtBlocks, txtRows, txtNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_ticket_sport);
        txtCode = findViewById(R.id.txtCode);
        imgQRCode = findViewById(R.id.imgQRCode);
        txtTitle = findViewById(R.id.txtTitle);
        txtAddress = findViewById(R.id.txtAddress);
        txtCategory = findViewById(R.id.txtCategory);
        txtOrganizer = findViewById(R.id.txtOrganizer);
        txtVenue = findViewById(R.id.txtVenue);
        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        txtGateway = findViewById(R.id.txtGateway);
        txtBlocks = findViewById(R.id.txtBlocks);
        txtBlocks = findViewById(R.id.txtBlocks);
        txtRows = findViewById(R.id.txtRows);
        txtNumbers = findViewById(R.id.txtNumbers);
        unitIcon = findViewById(R.id.unitIcon);
        txtHome = findViewById(R.id.txtHome);
    }

    @Override
    protected void initialGUI() {
        super.initialGUI();
        txtTitle.setText(ticketInfo.getEventName());
        txtMinAge.setText(ticketInfo.getGateway());
        txtDescription.setText(ticketInfo.getBlock());
        txtAddress.setText(ticketInfo.getAddress());
        txtDate.setText(ticketInfo.getReleaseDateAsString());
        txtTime.setText(ticketInfo.getTimeStr());
        switch (ticketInfo.getEventCategory()) {
            case 0: // Sport
                txtCategory.setText("Thể thao");
                break;
        }
        txtOrganizer.setText(ticketInfo.getHost());
        txtVenue.setText(ticketInfo.getLocation());
        txtGateway.setText(ticketInfo.getGateway());
        txtBlocks.setText(ticketInfo.getBlock().replace("Khán đài ", ""));
        Picasso.get().load(ticketInfo.getIconURL()).error(R.drawable.purchased_tickets).into(unitIcon);
        txtCode.setText(ticketInfo.getCode());

        // get rows, numbers
        String rows = "";
        String numbers = "";
        for (int i = 0; i < ticketInfo.getBookedSeats().size(); i++) {
            if (i == (ticketInfo.getBookedSeats().size() - 1)) {
                rows += (ticketInfo.getBookedSeats().get(i).getRow());
                numbers += (ticketInfo.getBookedSeats().get(i).getNumber());
            } else {
                rows += (ticketInfo.getBookedSeats().get(i).getRow() + "\n");
                numbers += (ticketInfo.getBookedSeats().get(i).getNumber() + "\n");
            }
        }
        // set rows, numbers
        txtRows.setText(rows);
        txtNumbers.setText(numbers);

        prepareQRCode();

        txtHome.setOnClickListener(v -> {
            Intent intent = new Intent(ResultTicketSportActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void prepareQRCode() {
        Bitmap bitmap = QRCode.from(ticketInfo.getCode()).bitmap();
        imgQRCode.setImageBitmap(bitmap);
    }
}
