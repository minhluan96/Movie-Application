package com.example.movieapp.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.models.Movie;
import com.example.movieapp.models.Sport;
import com.squareup.picasso.Picasso;

public class InfoFragment extends BaseFragment {

    private ImageView imgThumbnail, imgPlay;
    private TextView txtTitle, txtType, txtStartDate, txtTime, txtDescription, txtDirectors, txtActors;
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

        if (movie != null) {
            initMovieUI();
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
