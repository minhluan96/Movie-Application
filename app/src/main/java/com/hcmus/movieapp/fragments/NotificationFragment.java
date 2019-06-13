package com.hcmus.movieapp.fragments;

import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.adapters.NotificationAdapter;
import com.hcmus.movieapp.models.Notification;
import com.hcmus.movieapp.utils.AppManager;
import com.hcmus.movieapp.utils.DataParser;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NotificationFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private View v;
    private RecyclerView recyclerView;
    private List<Notification> notificationList;
    private NotificationAdapter notificationAdapter;
    private RecyclerView.LayoutManager notificationLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ShimmerFrameLayout shimmerLayout;

    private View notification, noNotificationInfo;

    private static final String TAG_NOTIFICATION = "TAG_NOTIFICATION";

    public  NotificationFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.notification_fragment, container, false);

        notification = v.findViewById(R.id.notification);
        noNotificationInfo = v.findViewById(R.id.noNotificationInfo);
        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout);
        shimmerLayout = v.findViewById(R.id.shimmer_view);

        notificationList = new ArrayList<>();

        notificationAdapter = new NotificationAdapter(getContext(), notificationList);

        getAllNotifications();

        recyclerView = v.findViewById(R.id.recycler_view);
        notificationLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(notificationLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(notificationAdapter);

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
        getAllNotifications();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void getAllNotifications() {
        showShimmer();
        AppManager.getInstance().getCommService().getAllNotifications(TAG_NOTIFICATION,
                new DataParser.DataResponseListener<LinkedList<Notification>>() {
                    @Override
                    public void onDataResponse(LinkedList<Notification> response) {
                        notificationList = response;

                        notificationAdapter.setNotifications(notificationList);

                        // Show notification layout, hide no notification info layout
                        notification.setVisibility(View.VISIBLE);
                        noNotificationInfo.setVisibility(View.GONE);

                        stopShimmer();
                    }

                    @Override
                    public void onDataError(String errorMessage) {
                        // Show no notification info layout, hide notification layout
                        noNotificationInfo.setVisibility(View.VISIBLE);
                        notification.setVisibility(View.GONE);
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
