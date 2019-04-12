package com.example.movieapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.movieapp.R;
import com.example.movieapp.adapters.ViewPagerHomeAdapter;
import com.example.movieapp.fragments.MovieHomeFragment;
import com.example.movieapp.fragments.SportHomeFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

public class HomeActivity extends BaseActivity implements OnTabSelectListener, OnTabReselectListener {

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

        bottomBar.setOnTabSelectListener(this);
        bottomBar.setOnTabReselectListener(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerHomeAdapter adapter = new ViewPagerHomeAdapter(getSupportFragmentManager());
        adapter.addFragment(movieFragment, "Phim chiếu rạp");
        adapter.addFragment(sportFragment, "Thể thao");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        if (tabId == R.id.tab_user) {
            //create an Intent to talk to activity: UserInfo
            Intent callUserInfo = new Intent(this, UserInfoActivity.class);
            startActivity(callUserInfo);
        }
    }
    @Override
    public void onTabReSelected (@IdRes int tabId) {
        if (tabId == R.id.tab_user) {
            //create an Intent to talk to activity: UserInfo
            Intent callUserInfo = new Intent(this, UserInfoActivity.class);
            startActivity(callUserInfo);
        }
    }
}
