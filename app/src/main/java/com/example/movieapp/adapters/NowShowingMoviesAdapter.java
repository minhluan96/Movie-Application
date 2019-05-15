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
import com.example.movieapp.models.Movie;
import com.example.movieapp.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NowShowingMoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Movie> nowShowingMovies;
    private Context mContext;
    private int type;

    public NowShowingMoviesAdapter(List<Movie> nowShowingMovies, Context mContext) {
        this.nowShowingMovies = nowShowingMovies;
        this.mContext = mContext;
    }

    public void setNowShowingMovies(List<Movie> nowShowingMovies) {
        this.nowShowingMovies = nowShowingMovies;
        this.notifyDataSetChanged();
    }

    public void addNowShowingMovies(List<Movie> nowShowingMovies) {
        this.nowShowingMovies.addAll(nowShowingMovies);
        this.notifyDataSetChanged();
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        int layout = R.layout.now_showing_movie_home_item;
        if (type == Constant.SizeThumbnail.SMALL) {
            layout = R.layout.now_showing_movie_home_item_small;
        }
        View view = LayoutInflater.from(mContext).inflate(layout, viewGroup, false);
        return new NowShowingMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        NowShowingMovieViewHolder nowShowingMovieViewHolder = (NowShowingMovieViewHolder) viewHolder;
        Movie movie = nowShowingMovies.get(i);
        nowShowingMovieViewHolder.txtTitle.setText(movie.getName());
        Picasso.get().load(movie.getImgURL()).error(R.drawable.poster).into(nowShowingMovieViewHolder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return nowShowingMovies.size();
    }

    class NowShowingMovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPoster;
        private TextView txtTitle;
        private Button btnBookMovie;

        public NowShowingMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            btnBookMovie = itemView.findViewById(R.id.btnBookTicket);
        }
    }
}
