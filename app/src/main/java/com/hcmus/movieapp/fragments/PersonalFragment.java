package com.hcmus.movieapp.fragments;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.adapters.ViewPagerHomeAdapter;
import com.hcmus.movieapp.utils.SaveSharedPreference;

public class PersonalFragment extends BaseFragment implements OnTabSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private LoggedOutFragment loggedOutFragment;
    private LoggedInFragment loggedInFragment;
    private PurchasedTicketsFragment purchasedTicketsFragment;
    private NotificationFragment notificationFragment;
    private static int tabSelected = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public PersonalFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.personal_fragment, container, false);

        tabLayout = v.findViewById(R.id.tabs);
        viewPager = v.findViewById(R.id.viewpager);
        toolbar = v.findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        loggedOutFragment = new LoggedOutFragment();
        loggedInFragment = new LoggedInFragment();
        purchasedTicketsFragment = new PurchasedTicketsFragment();
        notificationFragment = new NotificationFragment();

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(this);

        return v;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerHomeAdapter adapter = new ViewPagerHomeAdapter(getChildFragmentManager());
        // If account is already logged in
        if(SaveSharedPreference.getLoggedStatus(getContext())) {
            adapter.addFragment(loggedInFragment, "Tài khoản");
        } else {
            adapter.addFragment(loggedOutFragment, "Tài khoản");
        }
        adapter.addFragment(purchasedTicketsFragment, "Vé đã mua");
        adapter.addFragment(notificationFragment, "Thông báo");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        TabLayout.Tab tab = tabLayout.getTabAt(tabSelected);
        tab.select();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        tabSelected = tab.getPosition();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }
}
