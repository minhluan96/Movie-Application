package com.hcmus.movieapp.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.adapters.LatestSportsAdapter;
import com.hcmus.movieapp.adapters.UpcomingSportsAdapter;
import com.hcmus.movieapp.models.Sport;
import com.hcmus.movieapp.utils.AppManager;
import com.hcmus.movieapp.utils.DataParser;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SportHomeFragment extends BaseFragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ShimmerFrameLayout shimmerLatest, shimmerUpcoming;
    private RecyclerView rvLatestSports, rvUpcomingSports;
    private RecyclerView.LayoutManager latestSportLayoutManager, upcomingSportLayoutManager;
    private LatestSportsAdapter latestSportsAdapter;
    private UpcomingSportsAdapter upcomingSportsAdapter;

    private static final String TAG_HOTEST_EVENT = "TAG_HOTEST_EVENT";
    private static final String TAG_UPCOMING_EVENT = "TAG_UPCOMING_EVENT";
    private List<Sport> hotestSports = new ArrayList<>();
    private List<Sport> upcomingSports = new ArrayList<>();

    public SportHomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sport_home_fragment, container, false);
        swipeRefreshLayout = v.findViewById(R.id.swipe_layout);
        rvLatestSports = v.findViewById(R.id.rv_latest_sport);
        rvUpcomingSports = v.findViewById(R.id.rv_upcoming_sport);
        shimmerLatest = v.findViewById(R.id.shimmer_latest_view);
        shimmerUpcoming = v.findViewById(R.id.shimmer_upcoming_view);

        latestSportLayoutManager = new LinearLayoutManager(getContext());
        rvLatestSports.setLayoutManager(latestSportLayoutManager);
        setupLastestSportAdapter();

        upcomingSportLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvUpcomingSports.setLayoutManager(upcomingSportLayoutManager);
        setupUpcomingSportAdapter();

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

    private void setupLastestSportAdapter() {
        hotestSports = new ArrayList<>();
        latestSportsAdapter = new LatestSportsAdapter(hotestSports, getContext());
        rvLatestSports.setAdapter(latestSportsAdapter);
        getHotestSports();
    }

    private void setupUpcomingSportAdapter() {
        upcomingSports = new ArrayList<>();
        upcomingSportsAdapter = new UpcomingSportsAdapter(upcomingSports, getContext());
        rvUpcomingSports.setAdapter(upcomingSportsAdapter);
        getUpcomingSports();
    }

    private void getUpcomingSports() {
        showShimmer(shimmerUpcoming, rvUpcomingSports);
        AppManager.getInstance().getCommService().getUpcomingEvents(TAG_UPCOMING_EVENT, new DataParser.DataResponseListener<LinkedList<Sport>>() {
            @Override
            public void onDataResponse(LinkedList<Sport> result) {
                upcomingSports = result;
                upcomingSportsAdapter.setUpcomingSports(upcomingSports);
                stopShimmer(shimmerUpcoming, rvUpcomingSports);
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

    private void getHotestSports() {
        showShimmer(shimmerLatest, rvLatestSports);
        AppManager.getInstance().getCommService().getHotEvents(TAG_HOTEST_EVENT, new DataParser.DataResponseListener<LinkedList<Sport>>() {
            @Override
            public void onDataResponse(LinkedList<Sport> result) {
                hotestSports = result;
                latestSportsAdapter.setLatestSports(hotestSports);
                stopShimmer(shimmerLatest, rvLatestSports);
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
