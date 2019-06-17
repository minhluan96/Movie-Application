package com.hcmus.movieapp.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.activities.SellingTicketActivity;
import com.hcmus.movieapp.models.Movie;
import com.google.gson.Gson;
import com.hcmus.movieapp.utils.LoginListener;
import com.hcmus.movieapp.utils.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LatestMoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Movie> latestMovies;
    private Context mContext;
    private LoginListener loginListener;

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
        movieViewHolder.cardView.setPreventCornerOverlap(false);
        Picasso.get().load(movie.getImgURL()).transform(new RoundedTransformation(35, 0)).fit().error(R.drawable.poster).into(movieViewHolder.imgPoster);
        movieViewHolder.itemView.setOnClickListener(v -> {
            int pos = movieViewHolder.getAdapterPosition();
            loginListener.doLoginForMovie(latestMovies.get(pos));
        });
        movieViewHolder.btnBookTicket.setOnClickListener(v -> {
            int pos = movieViewHolder.getAdapterPosition();
            loginListener.doLoginForMovie(latestMovies.get(pos));
        });
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    @Override
    public int getItemCount() {
        return latestMovies.size();
    }

    class LatestMovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPoster;
        private TextView txtMinAge, txtScore, txtTitle;
        private CardView cardView;
        private Button btnBookTicket;

        public LatestMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            txtMinAge = itemView.findViewById(R.id.txtMinAge);
            txtScore = itemView.findViewById(R.id.txtScore);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            btnBookTicket = itemView.findViewById(R.id.btnBookTicket);
            cardView = itemView.findViewById(R.id.cardview);
        }

    }
}
