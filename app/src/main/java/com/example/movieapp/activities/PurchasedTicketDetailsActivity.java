package com.example.movieapp.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.movieapp.R;
import com.example.movieapp.fragments.UserInfoFormFragment;
import com.example.movieapp.models.Booking;

import java.util.HashMap;
import java.util.Map;

import static com.example.movieapp.utils.Utilities.formatTime;

public class PurchasedTicketDetailsActivity extends BaseActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private Map<Fragment, String> fragmentStringMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_activity);

        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewpager);

        Bundle bundle = getIntent().getExtras();
        Booking booking = bundle. getParcelable("purchased_ticket_info");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("BẮT ĐẦU LÚC " + formatTime(booking.getTime()));

//        userInfoFormFragment = new UserInfoFormFragment();
        fragmentStringMap = new HashMap<>();

//        fragmentStringMap.put(userInfoFormFragment, "Thông tin cá nhân");

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
