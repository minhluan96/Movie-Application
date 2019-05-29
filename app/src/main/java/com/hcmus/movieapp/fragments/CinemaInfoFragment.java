package com.hcmus.movieapp.fragments;

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.models.Cinema;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class CinemaInfoFragment extends BaseFragment {

    private ImageView imgThumbnail;
    private TextView txtTitle, txtAvgPoint, txtAddress, txtPhone, txtIntroduce, txtOperatingHours,
            txtIconAddress, txtIconPhone, txtIconOperatingHours;
    private Cinema cinema;
    public RatingBar ratingBar;

    public CinemaInfoFragment() {}

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cinema_info_fragment, container, false);
        imgThumbnail = v.findViewById(R.id.imgThumbnail);
        txtTitle = v.findViewById(R.id.txtTitle);
        txtAvgPoint = v.findViewById(R.id.txtAvgPoint);
        ratingBar = v.findViewById(R.id.ratingBar);
        txtPhone = v.findViewById(R.id.txtPhone);
        txtOperatingHours = v.findViewById(R.id.txtOperatingHours);
        txtAddress = v.findViewById(R.id.txtAddress);
        txtIntroduce = v.findViewById(R.id.txtIntroduce);

        txtIconAddress = v.findViewById(R.id.txtIconAddress);
        txtIconPhone = v.findViewById(R.id.txtIconPhone);
        txtIconOperatingHours = v.findViewById(R.id.txtIconOperatingHours);

        if (cinema != null) {
            initCinemaUI();
        }

        return v;
    }

    private void initCinemaUI() {
        if (cinema.getViewUrl() != null) {
            Picasso.get().load(cinema.getViewUrl()).error(R.drawable.poster).into(imgThumbnail);
        }
        txtTitle.setText(cinema.getName());

        DecimalFormat df = new DecimalFormat("#.#");
        String formatted = df.format(cinema.getAvgPoint());
        txtAvgPoint.setText(formatted);

        ratingBar.setStepSize(0.01f);
        float ratingValue = cinema.getAvgPoint() * 0.5f;
        ratingBar.setRating(ratingValue);

        txtPhone.setText(cinema.getPhone());
        txtOperatingHours.setText(cinema.getOperatingHours());
        txtAddress.setText(cinema.getAddress());
        txtIntroduce.setText(cinema.getIntroduce());

        // Set icon to TextView
        Drawable dr = this.getResources().getDrawable(R.drawable.location);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        // Scale it to 50 x 50
        Drawable drawable = new BitmapDrawable(this.getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));
        drawable.setColorFilter(this.getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        txtIconAddress.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

        dr = this.getResources().getDrawable(R.drawable.phone);
        bitmap = ((BitmapDrawable) dr).getBitmap();
        // Scale it to 50 x 50
        drawable = new BitmapDrawable(this.getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));
        drawable.setColorFilter(this.getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        txtIconPhone.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

        dr = this.getResources().getDrawable(R.drawable.operating_hours);
        bitmap = ((BitmapDrawable) dr).getBitmap();
        // Scale it to 50 x 50
        drawable = new BitmapDrawable(this.getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));
        drawable.setColorFilter(this.getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        txtIconOperatingHours.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }
}
