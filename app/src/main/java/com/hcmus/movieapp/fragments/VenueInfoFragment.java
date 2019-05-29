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
import com.hcmus.movieapp.models.Venue;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class VenueInfoFragment extends BaseFragment {

    private ImageView imgThumbnail;
    private TextView txtTitle, txtAvgPoint, txtAddress, txtCapacity, txtIntroduce,
            txtIconAddress, txtIconCapacity, txtIconSurface;
    private Venue venue;
    public RatingBar ratingBar;

    public VenueInfoFragment() {}

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.venue_info_fragment, container, false);
        imgThumbnail = v.findViewById(R.id.imgThumbnail);
        txtTitle = v.findViewById(R.id.txtTitle);
        txtAvgPoint = v.findViewById(R.id.txtAvgPoint);
        ratingBar = v.findViewById(R.id.ratingBar);
        txtCapacity = v.findViewById(R.id.txtCapacity);
        txtAddress = v.findViewById(R.id.txtAddress);
        txtIntroduce = v.findViewById(R.id.txtIntroduce);

        txtIconAddress = v.findViewById(R.id.txtIconAddress);
        txtIconCapacity = v.findViewById(R.id.txtIconCapacity);
        txtIconSurface = v.findViewById(R.id.txtIconSurface);

        if (venue != null) {
            initVenueUI();
        }

        return v;
    }

    private void initVenueUI() {
        if (venue.getViewUrl() != null) {
            Picasso.get().load(venue.getViewUrl()).error(R.drawable.poster).into(imgThumbnail);
        }
        txtTitle.setText(venue.getName());

        DecimalFormat df = new DecimalFormat("#.#");
        String formatted = df.format(venue.getAvgPoint());
        txtAvgPoint.setText(formatted);

        ratingBar.setStepSize(0.01f);
        float ratingValue = venue.getAvgPoint() * 0.5f;
        ratingBar.setRating(ratingValue);

        // Set thousands separator
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);

        txtCapacity.setText(formatter.format(venue.getCapacity()) + " chỗ ngồi");
        txtAddress.setText(venue.getAddress());
        txtIntroduce.setText(venue.getIntroduce());

        // Set icon to TextView
        Drawable dr = this.getResources().getDrawable(R.drawable.location);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        // Scale it to 50 x 50
        Drawable drawable = new BitmapDrawable(this.getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));
        drawable.setColorFilter(this.getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        txtIconAddress.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

        dr = this.getResources().getDrawable(R.drawable.seat);
        bitmap = ((BitmapDrawable) dr).getBitmap();
        // Scale it to 50 x 50
        drawable = new BitmapDrawable(this.getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));
        drawable.setColorFilter(this.getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        txtIconCapacity.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

        dr = this.getResources().getDrawable(R.drawable.stadium_surface);
        bitmap = ((BitmapDrawable) dr).getBitmap();
        // Scale it to 50 x 50
        drawable = new BitmapDrawable(this.getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));
        drawable.setColorFilter(this.getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        txtIconSurface.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }
}
