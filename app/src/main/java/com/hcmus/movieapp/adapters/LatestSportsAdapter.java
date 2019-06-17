package com.hcmus.movieapp.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.activities.SellingSportTicketActivity;
import com.hcmus.movieapp.models.Sport;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LatestSportsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Sport> latestSports;
    private Context mContext;

    public LatestSportsAdapter(List<Sport> latestSports, Context mContext) {
        this.latestSports = latestSports;
        this.mContext = mContext;
    }

    public void setLatestSports(List<Sport> latestSports) {
        this.latestSports = latestSports;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.latest_sport_item, viewGroup, false);
        return new LatestSportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        LatestSportViewHolder sportViewHolder = (LatestSportViewHolder) viewHolder;
        Sport sport = latestSports.get(i);
        sportViewHolder.txtTitle.setText(sport.getName());
        Picasso.get().load(sport.getImgUrl()).error(R.drawable.sports).into(sportViewHolder.imgPoster);

        sportViewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, SellingSportTicketActivity.class);
            int pos = sportViewHolder.getAdapterPosition();
            Gson gson = new Gson();
            String json = gson.toJson(latestSports.get(pos));
            intent.putExtra("title", latestSports.get(pos).getName());
            intent.putExtra("sport", json);
            mContext.startActivity(intent);
        });

        sportViewHolder.btnBuyTicket.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, SellingSportTicketActivity.class);
            int pos = sportViewHolder.getAdapterPosition();
            Gson gson = new Gson();
            String json = gson.toJson(latestSports.get(pos));
            intent.putExtra("title", latestSports.get(pos).getName());
            intent.putExtra("sport", json);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return latestSports.size();
    }

    class LatestSportViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPoster;
        private TextView txtTitle;
        private Button btnBuyTicket;

        public LatestSportViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            btnBuyTicket = itemView.findViewById(R.id.btnBookTicket);
        }
    }
}
