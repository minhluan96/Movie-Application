package com.hcmus.movieapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.activities.HomeActivity;
import com.hcmus.movieapp.activities.LoginActivity;
import com.hcmus.movieapp.adapters.PurchasedTicketsAdapter;
import com.hcmus.movieapp.models.Account;
import com.hcmus.movieapp.models.Booking;
import com.hcmus.movieapp.utils.AppManager;
import com.hcmus.movieapp.utils.DataParser;
import com.hcmus.movieapp.utils.SaveSharedPreference;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PurchasedTicketsFragment extends BaseFragment implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private View v;
    private RecyclerView recyclerView;
    private List<Booking> bookingList;
    private PurchasedTicketsAdapter purchasedTicketsAdapter;
    private RecyclerView.LayoutManager purchaseTicketsLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ShimmerFrameLayout shimmerLayout;

    private Button btnDirection;
    private TextView txtMessage;
    private Class classActivity;

    private View purchasedTickets, noPurchasedTicketsInfo;

    private Account accountInfo;

    private static final String TAG_PURCHASED_TICKETS = "TAG_PURCHASED_TICKETS";

    public PurchasedTicketsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.purchased_tickets_fragment, container, false);

        purchasedTickets = v.findViewById(R.id.purchasedTickets);
        noPurchasedTicketsInfo = v.findViewById(R.id.noPurchasedTicketsInfo);
        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout);
        shimmerLayout = v.findViewById(R.id.shimmer_view);

        accountInfo = SaveSharedPreference.getAccountInfo(getContext());

        bookingList = new ArrayList<>();

        purchasedTicketsAdapter = new PurchasedTicketsAdapter(getContext(), bookingList);

        recyclerView = v.findViewById(R.id.recycler_view);

        btnDirection = noPurchasedTicketsInfo.findViewById(R.id.btnDirection);
        txtMessage = noPurchasedTicketsInfo.findViewById(R.id.txtMessage);

        btnDirection.setOnClickListener(this);

        getAllBookingsByAccount();

        // If account is logged in
        if(SaveSharedPreference.getLoggedStatus(getContext())) {
            txtMessage.setText("Chưa có giao dịch nào trên TicketNow");
            btnDirection.setText("Đặt vé ngay bạn nhé!");
            classActivity = HomeActivity.class;
        }
        else {
            txtMessage.setText("Bạn cần đăng nhập để sử dụng tính năng này");
            btnDirection.setText("Đăng nhập");
            classActivity = LoginActivity.class;
        }

        purchaseTicketsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(purchaseTicketsLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(purchasedTicketsAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);

        return v;
    }

    private void showShimmer() {
        RecyclerView rv = v.findViewById(R.id.recycler_view);
        shimmerLayout.startShimmerAnimation();
        shimmerLayout.setVisibility(View.VISIBLE);
        rv.setVisibility(View.GONE);
    }

    private void stopShimmer() {
        RecyclerView rv = v.findViewById(R.id.recycler_view);
        shimmerLayout.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        getAllBookingsByAccount();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDirection) {
            //create an Intent to talk to activity: LoginActivity or HomeActivity
            Intent callActivity = new Intent(getActivity(), classActivity);
            startActivity(callActivity);
        }
    }

    private void getAllBookingsByAccount() {
        showShimmer();
        String accountID = String.valueOf(accountInfo.getId());
        AppManager.getInstance().getCommService().getBookingsByAccount(TAG_PURCHASED_TICKETS, accountID,
                new DataParser.DataResponseListener<LinkedList<Booking>>() {
                    @Override
                    public void onDataResponse(LinkedList<Booking> response) {
                        bookingList = response;

                        purchasedTicketsAdapter.setBookingsByAccount(bookingList);

                        // Show purchased tickets layout, hide no ticket info layout
                        purchasedTickets.setVisibility(View.VISIBLE);
                        noPurchasedTicketsInfo.setVisibility(View.GONE);

                        stopShimmer();
                    }

                    @Override
                    public void onDataError(String errorMessage) {
                        // Show no ticket info layout, hide purchased tickets layout
                        noPurchasedTicketsInfo.setVisibility(View.VISIBLE);
                        purchasedTickets.setVisibility(View.GONE);
                    }

                    @Override
                    public void onRequestError(String errorMessage, VolleyError volleyError) {
                        Log.e("API-PurchasedTickets", errorMessage);
                        Toast.makeText(getContext(), "Máy chủ bị lỗi! Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }
}
