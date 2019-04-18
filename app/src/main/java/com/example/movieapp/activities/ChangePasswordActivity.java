package com.example.movieapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.movieapp.R;
import com.example.movieapp.fragments.ChangePasswordFormFragment;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends BaseActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private ChangePasswordFormFragment changePasswordFormFragment;
    private Map<Fragment, String> fragmentStringMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_activity);

        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewpager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thay đổi mật khẩu");

        changePasswordFormFragment = new ChangePasswordFormFragment();
        fragmentStringMap = new HashMap<>();

        fragmentStringMap.put(changePasswordFormFragment, "Thay đổi mật khẩu");

        setupViewPager(viewPager, fragmentStringMap);
    }
}
