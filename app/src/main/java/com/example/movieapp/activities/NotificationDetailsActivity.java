package com.example.movieapp.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.movieapp.R;
import com.example.movieapp.fragments.NotificationInfoFragment;
import com.example.movieapp.models.Notification;

import java.util.HashMap;
import java.util.Map;

public class NotificationDetailsActivity extends BaseActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private Map<Fragment, String> fragmentStringMap;

    private NotificationInfoFragment notificationInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_details_activity);

        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewpager);

        Bundle bundle = getIntent().getExtras();
        Notification notification = bundle.getParcelable("notification_info");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getNotificationType(notification.getType()));

        bundle = new Bundle();
        bundle.putParcelable("notification_info", notification);
        notificationInfoFragment = new NotificationInfoFragment();
        notificationInfoFragment.setArguments(bundle);

        fragmentStringMap = new HashMap<>();

        fragmentStringMap.put(notificationInfoFragment, "Thông báo");

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

    public String getNotificationType(int type) {
        switch (type){
            case 0: return "Tin mới";
            case 1: return "Ưu đãi";
            default: return "";
        }
    }
}
