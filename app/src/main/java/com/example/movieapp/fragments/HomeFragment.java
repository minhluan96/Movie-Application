package com.example.movieapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieapp.R;
import com.example.movieapp.adapters.ViewPagerHomeAdapter;

public class HomeFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MovieHomeFragment movieFragment;
    private SportHomeFragment sportFragment;
    private Toolbar toolbar;

    public HomeFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);

        tabLayout = v.findViewById(R.id.tabs);
        viewPager = v.findViewById(R.id.viewpager);
        toolbar = v.findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);


        movieFragment = new MovieHomeFragment();
        sportFragment = new SportHomeFragment();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        return v;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerHomeAdapter adapter = new ViewPagerHomeAdapter(getChildFragmentManager());
        adapter.addFragment(movieFragment, "Phim chiếu rạp");
        adapter.addFragment(sportFragment, "Thể thao");
        viewPager.setAdapter(adapter);
    }
}
