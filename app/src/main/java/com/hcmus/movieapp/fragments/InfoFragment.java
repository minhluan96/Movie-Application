package com.hcmus.movieapp.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.models.Movie;
import com.hcmus.movieapp.models.Sport;
import com.hcmus.movieapp.utils.AppManager;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class InfoFragment extends BaseFragment {

    private TextView txtTitle, txtType, txtStartDate,
            txtTime, txtDescription, txtDirectors,
            txtActors, txtTitleDescription, txtTitleDirectors, txtTitleActors;
    private Movie movie;
    private Sport sport;
    private YouTubePlayerView youTubePlayerView;

    public InfoFragment() {

    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_fragment, container, false);
        txtTitle = v.findViewById(R.id.txtTitle);
        txtType = v.findViewById(R.id.txtType);
        txtStartDate = v.findViewById(R.id.txtStartDate);
        txtTime = v.findViewById(R.id.txtTime);
        txtDescription = v.findViewById(R.id.txtDescription);
        txtDirectors = v.findViewById(R.id.txtDirectors);
        txtActors = v.findViewById(R.id.txtActors);
        txtTitleDescription = v.findViewById(R.id.txtTitleDescription);
        txtTitleActors = v.findViewById(R.id.txtTitleActors);
        txtTitleDirectors = v.findViewById(R.id.txtTitleDirectors);
        youTubePlayerView = v.findViewById(R.id.youtube_player_view);

        getLifecycle().addObserver(youTubePlayerView);
        if (movie != null) {
            initMovieUI();
        } else {
            initSportUI();
        }

        return v;
    }

    private void initMovieUI() {
        if (movie.getImgURL() != null) {
            //Picasso.get().load(movie.getImgURL()).error(R.drawable.poster).into(imgThumbnail);
        }
        txtTitle.setText(movie.getName());
        txtType.setText(movie.getType());
        txtStartDate.setText(movie.getReleaseDate());
        txtTime.setText(movie.getLength());
        txtDescription.setText(movie.getDescription());
        txtDirectors.setText(movie.getDirectors());
        txtActors.setText(movie.getCasts());

        //imgPlay.setOnClickListener(v1 -> openExternalLink());
        openExternalLink();
    }

    private void initSportUI() {
        if (sport.getImgUrl() != null) {
            //Picasso.get().load(sport.getImgUrl()).error(R.drawable.sports).into(imgThumbnail);
        }
        txtTitle.setText(sport.getName());
        txtType.setText(sport.getReleaseDateStr());
        txtStartDate.setText(sport.getTimeStart());
        txtTime.setVisibility(View.GONE);
        txtTitleDescription.setText("Giới thiệu");
        txtDescription.setText(sport.getDescription());
        txtDirectors.setVisibility(View.GONE);
        txtActors.setVisibility(View.GONE);
        txtTitleDirectors.setVisibility(View.GONE);
        txtTitleActors.setVisibility(View.GONE);

        openExternalLink();
    }

    @Override
    public void onPause() {
        super.onPause();
        youTubePlayerView.release();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        youTubePlayerView.release();
    }

    private void openExternalLink() {
        if (movie != null) {
            int lastPos = movie.getTrailerURL().lastIndexOf("/");
            String idYoutube = movie.getTrailerURL().substring(lastPos + 1);
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {


                @Override
                public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.cueVideo(idYoutube, 0);
                }
            });
        } else {
            int lastPos = sport.getVideoUrl().lastIndexOf("/");
            String idYoutube = sport.getVideoUrl().substring(lastPos + 1);
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {


                @Override
                public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.cueVideo(idYoutube, 0);
                }
            });
        }

        /*Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + idYoutube));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + idYoutube));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }*/
    }
}
