package com.hcmus.movieapp.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.models.Sport;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UpcomingSportsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Sport> upcomingSports;
    private Context mContext;

    public UpcomingSportsAdapter(List<Sport> upcomingSports, Context mContext) {
        this.upcomingSports = upcomingSports;
        this.mContext = mContext;
    }

    public void setUpcomingSports(List<Sport> upcomingSports) {
        this.upcomingSports = upcomingSports;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.upcoming_sport_home_item, viewGroup, false);
        return new UpcomingSportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        UpcomingSportViewHolder upcomingSportViewHolder = (UpcomingSportViewHolder) viewHolder;
        Sport sport = upcomingSports.get(i);
        upcomingSportViewHolder.txtTitle.setText(sport.getName());
        Picasso.get().load(sport.getImgUrl()).error(R.drawable.sports).into(upcomingSportViewHolder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return upcomingSports.size();
    }

    class UpcomingSportViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPoster;
        private TextView txtTitle;

        public UpcomingSportViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            txtTitle = itemView.findViewById(R.id.txtTitle);
        }
    }
}
