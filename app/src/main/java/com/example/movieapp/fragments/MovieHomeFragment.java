package com.example.movieapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieapp.R;
import com.example.movieapp.adapters.LatestMoviesAdapter;
import com.example.movieapp.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieHomeFragment extends BaseFragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvLatestMovies;
    private LatestMoviesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public MovieHomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movie_home_fragment, container, false);
        swipeRefreshLayout = v.findViewById(R.id.swipe_layout);
        rvLatestMovies = v.findViewById(R.id.rv_latest_movie);

        layoutManager = new LinearLayoutManager(getContext());
        rvLatestMovies.setLayoutManager(layoutManager);
        setupLatestMovieAdapter();
        return v;
    }

    private void setupLatestMovieAdapter() {
        List<Movie> movies = getDummyMovies();
        adapter = new LatestMoviesAdapter(movies, getContext());
        rvLatestMovies.setAdapter(adapter);
    }

    private List<Movie> getDummyMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(1, "Captain Marvel",
                "https://m.media-amazon.com/images/M/MV5BMTE0YWFmOTMtYTU2ZS00ZTIxLWE3OTEtYTNiYzBkZjViZThiXkEyXkFqcGdeQXVyODMzMzQ4OTI@._V1_.jpg",
                "ĐANG CHIẾU", 7.4, "", 13));
        movies.add(new Movie(2, "Captain Marvel",
                "https://m.media-amazon.com/images/M/MV5BMTE0YWFmOTMtYTU2ZS00ZTIxLWE3OTEtYTNiYzBkZjViZThiXkEyXkFqcGdeQXVyODMzMzQ4OTI@._V1_.jpg",
                "ĐANG CHIẾU", 7.4, "", 13));
        movies.add(new Movie(3, "Captain Marvel",
                "https://m.media-amazon.com/images/M/MV5BMTE0YWFmOTMtYTU2ZS00ZTIxLWE3OTEtYTNiYzBkZjViZThiXkEyXkFqcGdeQXVyODMzMzQ4OTI@._V1_.jpg",
                "ĐANG CHIẾU", 7.4, "", 13));
        return movies;
    }
}
