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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.adapters.VenuesAdapter;
import com.hcmus.movieapp.models.Venue;
import com.hcmus.movieapp.utils.AppManager;
import com.hcmus.movieapp.utils.DataParser;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class VenuesFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private View v;
    private RecyclerView recyclerView;
    private List<Venue> venueList;
    private VenuesAdapter venuesAdapter;
    private RecyclerView.LayoutManager venuesLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ShimmerFrameLayout shimmerLayout;

    private View venuesView;
    private TextView txtNotify;

    private static final String TAG_LOCATION_VENUES = "TAG_LOCATION_VENUES";

    public VenuesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.venues_fragment, container, false);

        txtNotify = v.findViewById(R.id.txtNotify);
        venuesView = v.findViewById(R.id.venues);
        recyclerView = v.findViewById(R.id.recycler_view);
        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout);
        shimmerLayout = v.findViewById(R.id.shimmer_view);

        venueList = new ArrayList<>();

        venuesAdapter = new VenuesAdapter(getContext(), venueList);

        getAllVenues();

        venuesLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(venuesLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(venuesAdapter);

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
        getAllVenues();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void getAllVenues() {
        showShimmer();
        AppManager.getInstance().getCommService().getAllVenues(TAG_LOCATION_VENUES,
                new DataParser.DataResponseListener<LinkedList<Venue>>() {
                    @Override
                    public void onDataResponse(LinkedList<Venue> response) {
                        venueList = response;

                        venuesAdapter.setVenues(venueList);

                        venuesView.setVisibility(View.VISIBLE);
                        txtNotify.setVisibility(View.GONE);

                        stopShimmer();
                    }

                    @Override
                    public void onDataError(String errorMessage) {
                        txtNotify.setVisibility(View.VISIBLE);
                        venuesView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onRequestError(String errorMessage, VolleyError volleyError) {
                        Log.e("API-Venues", errorMessage);
                        Toast.makeText(getContext(), "Máy chủ bị lỗi! Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }
}
