package com.example.movieapp.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieapp.R;
import com.example.movieapp.adapters.ViewPagerHomeAdapter;
import com.example.movieapp.utils.SaveSharedPreference;

public class PersonalFragment extends BaseFragment implements OnTabSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private LoggedOutFragment loggedOutFragment;
    private LoggedInFragment loggedInFragment;
    private PurchasedTicketsFragment purchasedTicketsFragment;
    private NotificationFragment notificationFragment;

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
        int tabSelected = SaveSharedPreference.getLastSelectedTab(getContext());
        TabLayout.Tab tab = tabLayout.getTabAt(tabSelected);
        tab.select();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int tabSelected = tab.getPosition();
        SaveSharedPreference.setLastSelectedTab(getContext(), tabSelected);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }
}
