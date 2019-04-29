package com.example.movieapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.activities.SellingTicketActivity;
import com.example.movieapp.models.Movie;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LatestMoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Movie> latestMovies;
    private Context mContext;

    public LatestMoviesAdapter(List<Movie> latestMovies, Context mContext) {
        this.latestMovies = latestMovies;
        this.mContext = mContext;
    }

    public void setLatestMovies(List<Movie> latestMovies) {
        this.latestMovies = latestMovies;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.latest_movie_item, viewGroup, false);
        return new LatestMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        LatestMovieViewHolder movieViewHolder = (LatestMovieViewHolder) viewHolder;
        Movie movie = latestMovies.get(i);
        movieViewHolder.txtTitle.setText(movie.getName());
        movieViewHolder.txtScore.setText(movie.getScore() + "");
        movieViewHolder.txtMinAge.setText(movie.minAgeDisplay());
        Picasso.get().load(movie.getImgURL()).error(R.drawable.poster).into(movieViewHolder.imgPoster);
        movieViewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, SellingTicketActivity.class);
            int pos = movieViewHolder.getAdapterPosition();
            Gson gson = new Gson();
            String json = gson.toJson(latestMovies.get(pos));
            intent.putExtra("title", latestMovies.get(pos).getName());
            intent.putExtra("movie", json);
            mContext.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return latestMovies.size();
    }

    class LatestMovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPoster;
        private TextView txtMinAge, txtScore, txtTitle;
        private Button btnBookTicket;

        public LatestMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            txtMinAge = itemView.findViewById(R.id.txtMinAge);
            txtScore = itemView.findViewById(R.id.txtScore);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            btnBookTicket = itemView.findViewById(R.id.btnBookTicket);
        }

    }
}
