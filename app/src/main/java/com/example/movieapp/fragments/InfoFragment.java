package com.example.movieapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.R;

public class InfoFragment extends BaseFragment {

    private ImageView imgThumbnail, imgPlay;
    private TextView txtTitle, txtType, txtStartDate, txtTime, txtDescription;
    private RecyclerView rvActors;

    public InfoFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_fragment, container, false);
        imgThumbnail = v.findViewById(R.id.imgThumbnail);
        imgPlay = v.findViewById(R.id.btnPlayButton);
        txtTitle = v.findViewById(R.id.txtTitle);
        txtType = v.findViewById(R.id.txtType);
        txtStartDate = v.findViewById(R.id.txtStartDate);
        txtTime = v.findViewById(R.id.txtTime);
        rvActors = v.findViewById(R.id.rvActors);
        return v;
    }
}
