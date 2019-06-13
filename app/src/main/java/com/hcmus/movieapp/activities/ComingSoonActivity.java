package com.hcmus.movieapp.activities;

import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.VolleyError;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.adapters.UpcomingMoviesAdapter;
import com.hcmus.movieapp.models.Movie;
import com.hcmus.movieapp.utils.AppManager;
import com.hcmus.movieapp.utils.Constant;
import com.hcmus.movieapp.utils.DataParser;
import com.hcmus.movieapp.utils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ComingSoonActivity extends BaseActivity {

    private RecyclerView rvMovies;
    private GridLayoutManager layoutManager;
    private List<Movie> movieList;
    private UpcomingMoviesAdapter moviesAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private Toolbar toolbar;

    private static final String TAG_UPCOMING_MOVIES = "TAG_UPCOMING_MOVIES";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comming_soon);
        rvMovies = findViewById(R.id.rvMovies);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Phim sắp chiếu");

        layoutManager = new GridLayoutManager(this, 3);
        rvMovies.setLayoutManager(layoutManager);
        movieList = new ArrayList<>();
        moviesAdapter = new UpcomingMoviesAdapter(movieList, this);
        moviesAdapter.setType(Constant.SizeThumbnail.SMALL);
        rvMovies.setAdapter(moviesAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getUpcomingMovies(page);
            }
        };
        rvMovies.addOnScrollListener(scrollListener);
        getUpcomingMovies(1);
    }

    private void getUpcomingMovies(int page) {
        int perPage = 9;
        AppManager.getInstance().getCommService().getUpcomingMovies(TAG_UPCOMING_MOVIES, perPage, page,
                new DataParser.DataResponseListener<LinkedList<Movie>>() {
                    @Override
                    public void onDataResponse(LinkedList<Movie> result) {
                        movieList = result;
                        if (page == 1) {
                            moviesAdapter.setUpcomingMovies(movieList);
                        } else {
                            moviesAdapter.addNewUpcomingMovies(movieList);
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
