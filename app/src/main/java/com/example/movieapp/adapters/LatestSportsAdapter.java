package com.example.movieapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.models.Sport;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LatestSportsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Sport> latestSports;
    private Context mContext;

    public LatestSportsAdapter(List<Sport> latestSports, Context mContext) {
        this.latestSports = latestSports;
        this.mContext = mContext;
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
