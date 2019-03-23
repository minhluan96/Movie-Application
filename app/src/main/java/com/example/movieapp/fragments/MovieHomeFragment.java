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
import com.example.movieapp.adapters.NowShowingMoviesAdapter;
import com.example.movieapp.adapters.UpcomingMoviesAdapter;
import com.example.movieapp.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieHomeFragment extends BaseFragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvLatestMovies, rvNowShowingMovies, rvUpcomingMovies;
    private LatestMoviesAdapter latestMoviesAdapter;
    private NowShowingMoviesAdapter nowShowingMoviesAdapter;
    private UpcomingMoviesAdapter upcomingMoviesAdapter;
    private RecyclerView.LayoutManager latestMoviesLayoutManager, nowShowingMoviesLayoutManager, upComingMoviesLayoutManager;

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
        rvNowShowingMovies = v.findViewById(R.id.rv_now_showing_movie);
        rvUpcomingMovies = v.findViewById(R.id.rv_upcoming_movie);

        latestMoviesLayoutManager = new LinearLayoutManager(getContext());
        rvLatestMovies.setLayoutManager(latestMoviesLayoutManager);
        rvLatestMovies.setHasFixedSize(true);
        setupLatestMovieAdapter();

        nowShowingMoviesLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvNowShowingMovies.setLayoutManager(nowShowingMoviesLayoutManager);
        setupNowShowingMovieAdapter();

        upComingMoviesLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvUpcomingMovies.setLayoutManager(upComingMoviesLayoutManager);
        setupUpcomingMovieAdapter();

        return v;
    }

    private void setupNowShowingMovieAdapter() {
        List<Movie> movies = getDummyNowShowingMovies();
        nowShowingMoviesAdapter = new NowShowingMoviesAdapter(movies, getContext());
        rvNowShowingMovies.setAdapter(nowShowingMoviesAdapter);
    }

    private void setupUpcomingMovieAdapter() {
        List<Movie> movies = getDummyNowShowingMovies();
        upcomingMoviesAdapter = new UpcomingMoviesAdapter(movies, getContext());
        rvUpcomingMovies.setAdapter(upcomingMoviesAdapter);
    }

    private void setupLatestMovieAdapter() {
        List<Movie> movies = getDummyMovies();
        latestMoviesAdapter = new LatestMoviesAdapter(movies, getContext());
        rvLatestMovies.setAdapter(latestMoviesAdapter);
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

    private List<Movie> getDummyNowShowingMovies() {
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
        movies.add(new Movie(3, "Captain Marvel",
                "https://m.media-amazon.com/images/M/MV5BMTE0YWFmOTMtYTU2ZS00ZTIxLWE3OTEtYTNiYzBkZjViZThiXkEyXkFqcGdeQXVyODMzMzQ4OTI@._V1_.jpg",
                "ĐANG CHIẾU", 7.4, "", 13));
        movies.add(new Movie(3, "Captain Marvel",
                "https://m.media-amazon.com/images/M/MV5BMTE0YWFmOTMtYTU2ZS00ZTIxLWE3OTEtYTNiYzBkZjViZThiXkEyXkFqcGdeQXVyODMzMzQ4OTI@._V1_.jpg",
                "ĐANG CHIẾU", 7.4, "", 13));
        movies.add(new Movie(3, "Captain Marvel",
                "https://m.media-amazon.com/images/M/MV5BMTE0YWFmOTMtYTU2ZS00ZTIxLWE3OTEtYTNiYzBkZjViZThiXkEyXkFqcGdeQXVyODMzMzQ4OTI@._V1_.jpg",
                "ĐANG CHIẾU", 7.4, "", 13));
        return movies;
    }
}
