package com.hcmus.movieapp.activities;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.fragments.PurchasedTicketInfoFragment;
import com.hcmus.movieapp.models.Booking;
import com.hcmus.movieapp.utils.Utilities;

import java.util.HashMap;
import java.util.Map;

public class PurchasedTicketDetailsActivity extends BaseActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private Map<Fragment, String> fragmentStringMap;

    private PurchasedTicketInfoFragment purchasedTicketInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchased_ticket_details_activity);

        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewpager);

        Bundle bundle = getIntent().getExtras();
        Booking booking = bundle.getParcelable("purchased_ticket_info");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("BẮT ĐẦU LÚC " + Utilities.formatTime(booking.getTime()));

        bundle = new Bundle();
        bundle.putParcelable("purchased_ticket_info", booking);
        purchasedTicketInfoFragment = new PurchasedTicketInfoFragment();
        purchasedTicketInfoFragment.setArguments(bundle);

        fragmentStringMap = new HashMap<>();

        fragmentStringMap.put(purchasedTicketInfoFragment, "Thông tin vé đã mua");

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
