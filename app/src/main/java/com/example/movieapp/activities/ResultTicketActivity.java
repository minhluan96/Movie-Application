package com.example.movieapp.activities;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.movieapp.R;

import net.glxn.qrgen.android.QRCode;

public class ResultTicketActivity extends BaseActivity {

    private ImageView imgQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_ticket);
        imgQRCode = findViewById(R.id.imgQRCode);
        prepareQRCode();
    }

    private void prepareQRCode() {
        // TODO: using id of ticket instead of the invalid string
        Bitmap bitmap = QRCode.from("TicketNow").bitmap();
        imgQRCode.setImageBitmap(bitmap);
    }
}
