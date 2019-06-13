package com.hcmus.movieapp.activities;

import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.VolleyError;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.adapters.NowShowingMoviesAdapter;
import com.hcmus.movieapp.models.Movie;
import com.hcmus.movieapp.utils.AppManager;
import com.hcmus.movieapp.utils.Constant;
import com.hcmus.movieapp.utils.DataParser;
import com.hcmus.movieapp.utils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NowShowingActivity extends BaseActivity {

    private RecyclerView rvMovies;
    private GridLayoutManager layoutManager;
    private List<Movie> movieList;
    private NowShowingMoviesAdapter moviesAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private Toolbar toolbar;

    private static final String TAG_NOW_SHOWING_MOVIES = "TAG_NOW_SHOWING_MOVIES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_showing);
        rvMovies = findViewById(R.id.rvMovies);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Phim đang chiếu");

        layoutManager = new GridLayoutManager(this, 3);
        rvMovies.setLayoutManager(layoutManager);
        movieList = new ArrayList<>();
        moviesAdapter = new NowShowingMoviesAdapter(movieList, this);
        moviesAdapter.setType(Constant.SizeThumbnail.SMALL);
        rvMovies.setAdapter(moviesAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getNowShowingMovies(page);
            }
        };
        rvMovies.addOnScrollListener(scrollListener);
        getNowShowingMovies(1);
    }

    private void getNowShowingMovies(int page) {
        int perPage = 9;
        AppManager.getInstance().getCommService().getNowShowingMovies(TAG_NOW_SHOWING_MOVIES, perPage, page,
                new DataParser.DataResponseListener<LinkedList<Movie>>() {
                    @Override
                    public void onDataResponse(LinkedList<Movie> result) {
                        movieList = result;
                        if (page == 1) {
                            moviesAdapter.setNowShowingMovies(movieList);
                        } else {
                            moviesAdapter.addNowShowingMovies(movieList);
                        }
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
