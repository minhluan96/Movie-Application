package com.hcmus.movieapp.fragments;

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

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.adapters.ViewPagerHomeAdapter;

public class HomeFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MovieHomeFragment movieFragment;
    private SportHomeFragment sportFragment;
    private Toolbar toolbar;
    private static int tabSelected = 0;

    public HomeFragment() {}

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

    @Override
    public void onResume() {
        super.onResume();
        TabLayout.Tab tab = tabLayout.getTabAt(tabSelected);
        tab.select();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        tabSelected = tab.getPosition();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }
}
