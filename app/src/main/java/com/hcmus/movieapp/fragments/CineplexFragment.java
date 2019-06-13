package com.hcmus.movieapp.fragments;

import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.adapters.CineplexExpandableAdapter;
import com.hcmus.movieapp.models.Cinema;
import com.hcmus.movieapp.models.Cineplex;
import com.hcmus.movieapp.utils.AppManager;
import com.hcmus.movieapp.utils.DataParser;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CineplexFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private View v;
    private RecyclerView recyclerView;
    private List<Cineplex> cineplexList;
    private ShimmerFrameLayout shimmerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ExpandableListView expandableListView;
    private CineplexExpandableAdapter cineplexExpandableAdapter;
    private TextView txtNotify;
    private HashMap<Cineplex, List<Cinema>> listDataChild;

    private static final String TAG_LOCATION_CINEPLEX = "TAG_LOCATION_CINEPLEX";

    public CineplexFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.cineplex_fragment, container, false);

        shimmerLayout = v.findViewById(R.id.shimmer_view);
        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout);
        expandableListView = v.findViewById(R.id.list_expand);
        txtNotify = v.findViewById(R.id.txtNotify);
        recyclerView = v.findViewById(R.id.recycler_view);

        cineplexList = new ArrayList<>();

        cineplexExpandableAdapter = new CineplexExpandableAdapter(getContext(), cineplexList, listDataChild);
        expandableListView.setAdapter(cineplexExpandableAdapter);

        getAllCineplex();

        Display display =  getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                50, r.getDisplayMetrics());
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expandableListView.setIndicatorBounds(width - px, width);
        } else {
            expandableListView.setIndicatorBoundsRelative(width - px, width);
        }

        swipeRefreshLayout.setOnRefreshListener(this);

        return v;
    }

    private void showShimmer() {
        ExpandableListView elv = v.findViewById(R.id.list_expand);
        shimmerLayout.startShimmerAnimation();
        shimmerLayout.setVisibility(View.VISIBLE);
        elv.setVisibility(View.GONE);
    }

    private void stopShimmer() {
        ExpandableListView elv = v.findViewById(R.id.list_expand);
        shimmerLayout.setVisibility(View.GONE);
        elv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        getAllCineplex();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void getAllCineplex() {
        showShimmer();
        AppManager.getInstance().getCommService().getAllCineplex(TAG_LOCATION_CINEPLEX,
                new DataParser.DataResponseListener<LinkedList<Cineplex>>() {
                    @Override
                    public void onDataResponse(LinkedList<Cineplex> response) {
                        cineplexList = response;

                        txtNotify.setVisibility(View.GONE);
                        expandableListView.setVisibility(View.VISIBLE);
                        listDataChild = new HashMap<>();
                        for (Cineplex cineplex : cineplexList) {
                            List<Cinema> cinemas = Arrays.asList(cineplex.getCinemas());
                            listDataChild.put(cineplex, cinemas);
                        }
                        cineplexExpandableAdapter.setCineplexAndCinemas(cineplexList, listDataChild);

                        stopShimmer();
                    }

                    @Override
                    public void onDataError(String errorMessage) {
                        txtNotify.setVisibility(View.VISIBLE);
                        expandableListView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onRequestError(String errorMessage, VolleyError volleyError) {
                        Log.e("API-Cineplex", errorMessage);
                        Toast.makeText(getContext(), "Máy chủ bị lỗi! Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }
}
