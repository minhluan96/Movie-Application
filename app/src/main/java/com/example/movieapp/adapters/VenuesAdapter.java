package com.example.movieapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.models.Venue;

import java.text.DecimalFormat;
import java.util.List;

public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.MyViewHolder>{
    private Context context;
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
        this.context = context;
        this.venueList = venueList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.venue_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {
        final Venue item = venueList.get(position);

        viewHolder.name.setText(item.getName());
        viewHolder.address.setText(item.getAddress());

        DecimalFormat df = new DecimalFormat("#.#");
        String formatted = df.format(item.getAvgPoint());
        viewHolder.avgPoint.setText(formatted);

        viewHolder.ratingBar.setStepSize(0.01f);
        float ratingValue = item.getAvgPoint() * 0.5f;
        viewHolder.ratingBar.setRating(ratingValue);

        viewHolder.itemView.setOnClickListener(v -> {
            //TODO: create an Intent to talk to activity
            //Intent intent = new Intent(context, PurchasedTicketDetailsActivity.class);
            //int pos = MyViewHolder.getAdapterPosition();
            //intent.putExtra("venue_info", venueList.get(pos));
            //context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return venueList.size();
    }

    public void setVenues(List<Venue> venues) {
        this.venueList = venues;
        this.notifyDataSetChanged();
    }
}
