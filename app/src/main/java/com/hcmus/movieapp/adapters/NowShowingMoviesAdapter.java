package com.hcmus.movieapp.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.activities.SellingTicketActivity;
import com.hcmus.movieapp.models.Movie;
import com.hcmus.movieapp.utils.Constant;
import com.hcmus.movieapp.utils.LoginListener;
import com.hcmus.movieapp.utils.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NowShowingMoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Movie> nowShowingMovies;
    private LoginListener loginListener;
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
        nowShowingMovieViewHolder.cardView.setPreventCornerOverlap(false);
        nowShowingMovieViewHolder.btnBookMovie.setOnClickListener(v -> {

            int pos = nowShowingMovieViewHolder.getAdapterPosition();
            loginListener.doLoginForMovie(nowShowingMovies.get(pos));
            /*Intent intent = new Intent(mContext, SellingTicketActivity.class);
            Gson gson = new Gson();
            String json = gson.toJson(nowShowingMovies.get(pos));
            intent.putExtra("title", nowShowingMovies.get(pos).getName());
            intent.putExtra("movie", json);
            mContext.startActivity(intent);*/
        });
        nowShowingMovieViewHolder.itemView.setOnClickListener(v -> {
            int pos = nowShowingMovieViewHolder.getAdapterPosition();
            loginListener.doLoginForMovie(nowShowingMovies.get(pos));

            /*Intent intent = new Intent(mContext, SellingTicketActivity.class);
            int pos = nowShowingMovieViewHolder.getAdapterPosition();
            Gson gson = new Gson();
            String json = gson.toJson(nowShowingMovies.get(pos));
            intent.putExtra("title", nowShowingMovies.get(pos).getName());
            intent.putExtra("movie", json);
            mContext.startActivity(intent);*/
        });
        Picasso.get().load(movie.getImgURL())
                .transform(new RoundedTransformation(30, 0)).fit()
                .error(R.drawable.poster).into(nowShowingMovieViewHolder.imgPoster);
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    @Override
    public int getItemCount() {
        return nowShowingMovies.size();
    }

    class NowShowingMovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPoster;
        private TextView txtTitle;
        private Button btnBookMovie;
        private CardView cardView;

        public NowShowingMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            cardView = itemView.findViewById(R.id.cardview);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            btnBookMovie = itemView.findViewById(R.id.btnBookTicket);
        }
    }
}
