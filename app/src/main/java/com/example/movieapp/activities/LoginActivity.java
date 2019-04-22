package com.example.movieapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.movieapp.R;
import com.example.movieapp.fragments.LoginFormFragment;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private LoginFormFragment loginFormFragment;
    private Map<Fragment, String> fragmentStringMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewpager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đăng nhập");

        loginFormFragment = new LoginFormFragment();
        fragmentStringMap = new HashMap<>();

        fragmentStringMap.put(loginFormFragment, "Đăng nhập");

        setupViewPager(viewPager, fragmentStringMap);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
