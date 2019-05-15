package com.example.movieapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.movieapp.R;
import com.example.movieapp.activities.ComingSoonActivity;
import com.example.movieapp.activities.NowShowingActivity;
import com.example.movieapp.adapters.LatestMoviesAdapter;
import com.example.movieapp.adapters.NowShowingMoviesAdapter;
import com.example.movieapp.adapters.UpcomingMoviesAdapter;
import com.example.movieapp.models.Movie;
import com.example.movieapp.utils.AppManager;
import com.example.movieapp.utils.DataParser;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MovieHomeFragment extends BaseFragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ShimmerFrameLayout shimmerLatest, shimmerNowShowing, shimmerUpcoming;
    private RecyclerView rvLatestMovies, rvNowShowingMovies, rvUpcomingMovies;
    private LatestMoviesAdapter latestMoviesAdapter;
    private NowShowingMoviesAdapter nowShowingMoviesAdapter;
    private UpcomingMoviesAdapter upcomingMoviesAdapter;
    private RecyclerView.LayoutManager latestMoviesLayoutManager, nowShowingMoviesLayoutManager, upComingMoviesLayoutManager;
    private TextView txtUpcomingShowAll, txtNowShowingShowAll;

    private static final String TAG_LATEST_MOVIES = "TAG_LATEST_MOVIES";
    private static final String TAG_NOW_SHOWING_MOVIES = "TAG_NOW_SHOWING_MOVIES";
    private static final String TAG_UPCOMING_MOVIES = "TAG_UPCOMING_MOVIES";

    private List<Movie> latestMovies = new ArrayList<>();
    private List<Movie> upcomingMovies = new ArrayList<>();
    private List<Movie> nowShowingMovies = new ArrayList<>();

    private int page = 1;
    private int size = 8;

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
        shimmerLatest = v.findViewById(R.id.shimmer_latest_view);
        shimmerNowShowing = v.findViewById(R.id.shimmer_now_showing_view);
        shimmerUpcoming = v.findViewById(R.id.shimmer_upcoming_view);
        txtUpcomingShowAll = v.findViewById(R.id.txtUpcomingShowAll);
        txtNowShowingShowAll = v.findViewById(R.id.txtNowShowingShowAll);

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


        getAllLatestMovies();
        getUpcomingMovies();
        getNowShowingMovies();

        txtUpcomingShowAll.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), ComingSoonActivity.class);
            startActivity(intent);
        });

        txtNowShowingShowAll.setOnClickListener(v12 -> {
            Intent intent = new Intent(getActivity(), NowShowingActivity.class);
            startActivity(intent);
        });

        return v;
    }

    private void showShimmer(ShimmerFrameLayout shimmer, View view) {
        shimmer.startShimmerAnimation();
        shimmer.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);
    }

    private void stopShimmer(ShimmerFrameLayout shimmer, View view) {
        shimmer.stopShimmerAnimation();
        shimmer.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
    }

    private void setupNowShowingMovieAdapter() {
        nowShowingMoviesAdapter = new NowShowingMoviesAdapter(nowShowingMovies, getContext());
        rvNowShowingMovies.setAdapter(nowShowingMoviesAdapter);
    }

    private void setupUpcomingMovieAdapter() {
        upcomingMoviesAdapter = new UpcomingMoviesAdapter(upcomingMovies, getContext());
        rvUpcomingMovies.setAdapter(upcomingMoviesAdapter);
    }

    private void setupLatestMovieAdapter() {
        latestMoviesAdapter = new LatestMoviesAdapter(latestMovies, getContext());
        rvLatestMovies.setAdapter(latestMoviesAdapter);
    }

    private void getNowShowingMovies() {
        showShimmer(shimmerNowShowing, rvNowShowingMovies);
        AppManager.getInstance().getCommService().getNowShowingMovies(TAG_NOW_SHOWING_MOVIES, size, page,
                new DataParser.DataResponseListener<LinkedList<Movie>>() {
                    @Override
                    public void onDataResponse(LinkedList<Movie> result) {
                        nowShowingMovies = result;
                        nowShowingMoviesAdapter.setNowShowingMovies(nowShowingMovies);
                        stopShimmer(shimmerNowShowing, rvNowShowingMovies);
                    }

                    @Override
                    public void onDataError(String errorMessage) {

                    }

                    @Override
                    public void onRequestError(String errorMessage, VolleyError volleyError) {

                    }

                    @Override
                    public void onCancel() {

                    }
                });

    }

    private void getAllLatestMovies() {
        showShimmer(shimmerLatest, rvLatestMovies);
        AppManager.getInstance().getCommService().getLatestMovies(TAG_LATEST_MOVIES,
                new DataParser.DataResponseListener<LinkedList<Movie>>() {
                    @Override
                    public void onDataResponse(LinkedList<Movie> result) {
                        latestMovies = result;
                        latestMoviesAdapter.setLatestMovies(latestMovies);
                        stopShimmer(shimmerLatest, rvLatestMovies);
                    }

                    @Override
                    public void onDataError(String errorMessage) {

                    }

                    @Override
                    public void onRequestError(String errorMessage, VolleyError volleyError) {

                    }

                    @Override
                    public void onCancel() {

                    }
                });

    }


    private void getUpcomingMovies() {
        showShimmer(shimmerUpcoming, rvUpcomingMovies);
        AppManager.getInstance().getCommService().getUpcomingMovies(TAG_UPCOMING_MOVIES, size, page,
                new DataParser.DataResponseListener<LinkedList<Movie>>() {
            @Override
            public void onDataResponse(LinkedList<Movie> result) {
                upcomingMovies = result;
                upcomingMoviesAdapter.setUpcomingMovies(upcomingMovies);
                stopShimmer(shimmerUpcoming, rvUpcomingMovies);
            }

            @Override
            public void onDataError(String errorMessage) {

            }

            @Override
            public void onRequestError(String errorMessage, VolleyError volleyError) {

            }

            @Override
            public void onCancel() {

            }
        });

    }
}
