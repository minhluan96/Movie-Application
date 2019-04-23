package com.example.movieapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.movieapp.R;
import com.example.movieapp.fragments.RegistrationFormFragment;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends BaseActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private RegistrationFormFragment registrationFormFragment;
    private Map<Fragment, String> fragmentStringMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewpager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đăng ký");

        registrationFormFragment = new RegistrationFormFragment();
        fragmentStringMap = new HashMap<>();

        fragmentStringMap.put(registrationFormFragment, "Đăng ký");

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
