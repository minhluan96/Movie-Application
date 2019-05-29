package com.hcmus.movieapp.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.models.Movie;
import com.hcmus.movieapp.models.Sport;
import com.hcmus.movieapp.utils.AppManager;
import com.squareup.picasso.Picasso;

public class InfoFragment extends BaseFragment {

    private ImageView imgThumbnail, imgPlay;
    private TextView txtTitle, txtType, txtStartDate,
            txtTime, txtDescription, txtDirectors,
            txtActors, txtTitleDescription, txtTitleDirectors, txtTitleActors;
    private Movie movie;
    private Sport sport;

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
        imgThumbnail = v.findViewById(R.id.imgThumbnail);
        imgPlay = v.findViewById(R.id.btnPlayButton);
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

        if (movie != null) {
            initMovieUI();
        } else {
            initSportUI();
        }

        return v;
    }

    private void initMovieUI() {
        if (movie.getImgURL() != null) {
            Picasso.get().load(movie.getImgURL()).error(R.drawable.poster).into(imgThumbnail);
        }
        txtTitle.setText(movie.getName());
        txtType.setText(movie.getType());
        txtStartDate.setText(movie.getReleaseDate());
        txtTime.setText(movie.getLength());
        txtDescription.setText(movie.getDescription());
        txtDirectors.setText(movie.getDirectors());
        txtActors.setText(movie.getCasts());

        imgPlay.setOnClickListener(v1 -> openExternalLink());
    }

    private void initSportUI() {
        if (sport.getImgUrl() != null) {
            Picasso.get().load(sport.getImgUrl()).error(R.drawable.sports).into(imgThumbnail);
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
    }

    private void openExternalLink() {
        int lastPos = movie.getTrailerURL().lastIndexOf("/");
        String idYoutube = movie.getTrailerURL().substring(lastPos + 1);
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + idYoutube));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + idYoutube));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}