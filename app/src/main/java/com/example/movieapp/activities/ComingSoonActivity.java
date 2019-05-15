package com.example.movieapp.activities;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.VolleyError;
import com.example.movieapp.R;
import com.example.movieapp.adapters.NowShowingMoviesAdapter;
import com.example.movieapp.adapters.UpcomingMoviesAdapter;
import com.example.movieapp.models.Movie;
import com.example.movieapp.utils.AppManager;
import com.example.movieapp.utils.Constant;
import com.example.movieapp.utils.DataParser;
import com.example.movieapp.utils.EndlessRecyclerViewScrollListener;
import com.example.movieapp.utils.ItemGridDecorator;
import com.example.movieapp.utils.ItemOffsetDecoration;

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
