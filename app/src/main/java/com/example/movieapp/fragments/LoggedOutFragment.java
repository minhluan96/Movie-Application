package com.example.movieapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.movieapp.R;
import com.example.movieapp.activities.LoginActivity;

public class LoggedOutFragment extends BaseFragment implements OnClickListener {

    private ImageView imgAvatar;

    public LoggedOutFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.logged_out_fragment, container, false);

        imgAvatar = v.findViewById(R.id.imgAvatar);

        imgAvatar.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgAvatar) {
            //create an Intent to talk to activity: LoginActivity
            Intent callLogin = new Intent(getActivity(), LoginActivity.class);
            startActivity(callLogin);
        }
    }
}
