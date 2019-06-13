package com.hcmus.movieapp.activities;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.fragments.ChangePasswordFormFragment;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends BaseActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private ChangePasswordFormFragment changePasswordFormFragment;
    private Map<Fragment, String> fragmentStringMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
