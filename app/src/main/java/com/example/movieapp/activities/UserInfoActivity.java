package com.example.movieapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.movieapp.R;
import com.example.movieapp.adapters.ViewPagerHomeAdapter;
import com.example.movieapp.fragments.AccountFragment;
import com.example.movieapp.fragments.NotificationFragment;
import com.example.movieapp.fragments.PurchasedTicketsFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

public class UserInfoActivity extends BaseActivity implements OnTabSelectListener, OnTabReselectListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BottomBar bottomBar;
    private AccountFragment accountFragment;
    private PurchasedTicketsFragment purchasedTicketsFragment;
    private NotificationFragment notificationFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_activity);

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
        bottomBar = findViewById(R.id.bottomBar);

        accountFragment = new AccountFragment();
        purchasedTicketsFragment = new PurchasedTicketsFragment();
        notificationFragment = new NotificationFragment();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        bottomBar.setOnTabSelectListener(this);
        bottomBar.setOnTabReselectListener(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerHomeAdapter adapter = new ViewPagerHomeAdapter(getSupportFragmentManager());
        adapter.addFragment(accountFragment, "Tài khoản");
        adapter.addFragment(purchasedTicketsFragment, "Vé đã mua");
        adapter.addFragment(notificationFragment, "Thông báo");
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
