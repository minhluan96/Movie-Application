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

import com.android.volley.VolleyError;
import com.example.movieapp.R;
import com.example.movieapp.adapters.LatestSportsAdapter;
import com.example.movieapp.adapters.UpcomingSportsAdapter;
import com.example.movieapp.models.Sport;
import com.example.movieapp.utils.AppManager;
import com.example.movieapp.utils.DataParser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SportHomeFragment extends BaseFragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvLatestSports, rvUpcomingSports;
    private RecyclerView.LayoutManager latestSportLayoutManager, upcomingSportLayoutManager;
    private LatestSportsAdapter latestSportsAdapter;
    private UpcomingSportsAdapter upcomingSportsAdapter;

    private static final String TAG_HOTEST_EVENT = "TAG_HOTEST_EVENT";
    private List<Sport> hotestSports = new ArrayList<>();

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

        latestSportLayoutManager = new LinearLayoutManager(getContext());
        rvLatestSports.setLayoutManager(latestSportLayoutManager);
        setupLastestSportAdapter();

        upcomingSportLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvUpcomingSports.setLayoutManager(upcomingSportLayoutManager);
        setupUpcomingSportAdapter();

        return v;
    }


    private void setupLastestSportAdapter() {
        hotestSports = new ArrayList<>();
        latestSportsAdapter = new LatestSportsAdapter(hotestSports, getContext());
        rvLatestSports.setAdapter(latestSportsAdapter);
        getHotestSports();
    }

    private void setupUpcomingSportAdapter() {
        List<Sport> sports = getDummyUpcomingSports();
        upcomingSportsAdapter = new UpcomingSportsAdapter(sports, getContext());
        rvUpcomingSports.setAdapter(upcomingSportsAdapter);
    }

    private List<Sport> getDummyLatestSport() {
        List<Sport> sports = new ArrayList<>();
        sports.add(new Sport(1, "Vietnam - Indonesia", "http://img.f51.bdpcdn.net/Assets/Media/2019/01/08/62/vietnam-iran-480.jpg", "ĐANG DIỄN RA", ""));
        sports.add(new Sport(2, "Vietnam - Australia", "http://img.f51.bdpcdn.net/Assets/Media/2019/01/08/62/vietnam-iran-480.jpg", "ĐANG DIỄN RA", ""));
        sports.add(new Sport(3, "Vietnam - Iraq", "http://img.f51.bdpcdn.net/Assets/Media/2019/01/08/62/vietnam-iran-480.jpg", "ĐANG DIỄN RA", ""));
        return sports;
    }

    private List<Sport> getDummyUpcomingSports() {
        List<Sport> sports = new ArrayList<>();
        sports.add(new Sport(1, "Vietnam - Indonesia", "http://img.f51.bdpcdn.net/Assets/Media/2019/01/08/62/vietnam-iran-480.jpg", "ĐANG DIỄN RA", ""));
        sports.add(new Sport(2, "Vietnam - Australia", "http://img.f51.bdpcdn.net/Assets/Media/2019/01/08/62/vietnam-iran-480.jpg", "ĐANG DIỄN RA", ""));
        sports.add(new Sport(3, "Vietnam - Iraq", "http://img.f51.bdpcdn.net/Assets/Media/2019/01/08/62/vietnam-iran-480.jpg", "ĐANG DIỄN RA", ""));
        sports.add(new Sport(4, "Vietnam - Iraq", "http://img.f51.bdpcdn.net/Assets/Media/2019/01/08/62/vietnam-iran-480.jpg", "ĐANG DIỄN RA", ""));
        sports.add(new Sport(5, "Vietnam - Iraq", "http://img.f51.bdpcdn.net/Assets/Media/2019/01/08/62/vietnam-iran-480.jpg", "ĐANG DIỄN RA", ""));
        sports.add(new Sport(6, "Vietnam - Iraq", "http://img.f51.bdpcdn.net/Assets/Media/2019/01/08/62/vietnam-iran-480.jpg", "ĐANG DIỄN RA", ""));
        return sports;
    }

    private void getHotestSports() {
        AppManager.getInstance().getCommService().getHotEvents(TAG_HOTEST_EVENT, new DataParser.DataResponseListener<LinkedList<Sport>>() {
            @Override
            public void onDataResponse(LinkedList<Sport> result) {
                hotestSports = result;
                latestSportsAdapter.setLatestSports(hotestSports);
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
