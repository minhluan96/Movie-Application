package com.example.movieapp.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;


import com.example.movieapp.R;
import com.example.movieapp.adapters.ViewPagerHomeAdapter;
import com.example.movieapp.fragments.MovieHomeFragment;
import com.example.movieapp.fragments.SportHomeFragment;
import com.roughike.bottombar.BottomBar;

public class HomeActivity extends BaseActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BottomBar bottomBar;
    private MovieHomeFragment movieFragment;
    private SportHomeFragment sportFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
        bottomBar = findViewById(R.id.bottomBar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movieFragment = new MovieHomeFragment();
        sportFragment = new SportHomeFragment();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerHomeAdapter adapter = new ViewPagerHomeAdapter(getSupportFragmentManager());
        adapter.addFragment(movieFragment, "Phim chiếu rạp");
        adapter.addFragment(sportFragment, "Thể thao");
        viewPager.setAdapter(adapter);
    }
}
