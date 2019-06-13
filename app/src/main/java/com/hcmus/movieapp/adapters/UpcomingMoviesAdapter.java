package com.hcmus.movieapp.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.models.Movie;
import com.hcmus.movieapp.utils.Constant;
import com.hcmus.movieapp.utils.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UpcomingMoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Movie> upcomingMovies;
    private Context mContext;
    private int type;

    public UpcomingMoviesAdapter(List<Movie> upcomingMovies, Context mContext) {
        this.upcomingMovies = upcomingMovies;
        this.mContext = mContext;
    }

    public void setUpcomingMovies(List<Movie> upcomingMovies) {
        this.upcomingMovies = upcomingMovies;
        this.notifyDataSetChanged();
    }

    public void addNewUpcomingMovies(List<Movie> upcomingMovies) {
        this.upcomingMovies.addAll(upcomingMovies);
        this.notifyDataSetChanged();
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        int layout = R.layout.upcoming_movie_home_item;
        if (type == Constant.SizeThumbnail.SMALL) {
            layout = R.layout.upcoming_movie_home_item_small;
        }
        View view = LayoutInflater.from(mContext).inflate(layout, viewGroup, false);
        return new UpcomingMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        UpcomingMovieViewHolder upcomingMovieViewHolder = (UpcomingMovieViewHolder) viewHolder;
        Movie movie = upcomingMovies.get(i);
        upcomingMovieViewHolder.txtTitle.setText(movie.getName());
        upcomingMovieViewHolder.cardView.setPreventCornerOverlap(false);
        Picasso.get().load(movie.getImgURL()).transform(new RoundedTransformation(30, 0)).fit()
                .error(R.drawable.poster).into(upcomingMovieViewHolder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return upcomingMovies.size();
    }

    class UpcomingMovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPoster;
        private TextView txtTitle;
        private CardView cardView;

        public UpcomingMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            txtTitle = itemView.findViewById(R.id.txtTitle);
        }
    }
}
