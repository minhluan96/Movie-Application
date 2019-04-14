package com.example.movieapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UpcomingMoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Movie> upcomingMovies;
    private Context mContext;

    public UpcomingMoviesAdapter(List<Movie> upcomingMovies, Context mContext) {
        this.upcomingMovies = upcomingMovies;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.upcoming_movie_home_item, viewGroup, false);
        return new UpcomingMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        UpcomingMovieViewHolder upcomingMovieViewHolder = (UpcomingMovieViewHolder) viewHolder;
        Movie movie = upcomingMovies.get(i);
        upcomingMovieViewHolder.txtTitle.setText(movie.getName());
        Picasso.get().load(movie.getImgURL()).error(R.drawable.poster).into(upcomingMovieViewHolder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return upcomingMovies.size();
    }

    class UpcomingMovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPoster;
        private TextView txtTitle;

        public UpcomingMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            txtTitle = itemView.findViewById(R.id.txtTitle);
        }
    }
}
