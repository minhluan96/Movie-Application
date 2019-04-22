package com.example.movieapp.activities;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.movieapp.R;
import com.example.movieapp.fragments.HomeFragment;
import com.example.movieapp.fragments.PersonalFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class HomeActivity extends BaseActivity implements OnTabSelectListener {

    private BottomBar bottomBar;
    private FrameLayout containerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        bottomBar = findViewById(R.id.bottomBar);

        containerLayout = findViewById(R.id.container_layout);
        bottomBar.setOnTabSelectListener(this);
    }

    @Override
    public void onTabSelected(int tabId) {
        if (tabId == R.id.tab_trending) {
            HomeFragment homeFragment = new HomeFragment();
            showFragment(homeFragment, R.id.container_layout);
        } else if (tabId == R.id.tab_theater) {
            // TODO: Implement tab for selling ticket places here
        } else if (tabId == R.id.tab_user) {
            PersonalFragment personalFragment = new PersonalFragment();
            showFragment(personalFragment, R.id.container_layout);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        int tabSelected = bottomBar.getCurrentTabId();
        if (tabSelected == R.id.tab_trending) {
            HomeFragment homeFragment = new HomeFragment();
            showFragment(homeFragment, R.id.container_layout);
        } else if (tabSelected == R.id.tab_theater) {
            // TODO: Implement tab for selling ticket places here
        } else if (tabSelected == R.id.tab_user) {
            PersonalFragment personalFragment = new PersonalFragment();
            showFragment(personalFragment, R.id.container_layout);
        }
    }
}
