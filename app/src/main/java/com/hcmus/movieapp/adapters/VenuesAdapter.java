package com.hcmus.movieapp.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.activities.VenueInfoActivity;
import com.hcmus.movieapp.models.Venue;

import java.text.DecimalFormat;
import java.util.List;

public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.MyViewHolder>{
    private Context mContext;
    private List<Venue> venueList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, address, avgPoint;
        public RatingBar ratingBar;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.txtVenueName);
            address = view.findViewById(R.id.txtAddress);
            avgPoint = view.findViewById(R.id.txtAvgPoint);
            ratingBar = view.findViewById(R.id.ratingBar);
        }
    }

    public VenuesAdapter(Context context, List<Venue> venueList) {
        this.mContext = context;
        this.venueList = venueList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.venue_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {
        final Venue venue = venueList.get(position);

        viewHolder.name.setText(venue.getName());
        viewHolder.address.setText(venue.getAddress());

        DecimalFormat df = new DecimalFormat("#.#");
        String formatted = df.format(venue.getAvgPoint());
        viewHolder.avgPoint.setText(formatted);

        viewHolder.ratingBar.setStepSize(0.01f);
        float ratingValue = venue.getAvgPoint() * 0.5f;
        viewHolder.ratingBar.setRating(ratingValue);

        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, VenueInfoActivity.class);
            Gson gson = new Gson();
            String json = gson.toJson(venue);
            intent.putExtra("title", venue.getName());
            intent.putExtra("venue", json);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return venueList.size();
    }

    public void setVenues(List<Venue> venueList) {
        this.venueList = venueList;
        this.notifyDataSetChanged();
    }
}
