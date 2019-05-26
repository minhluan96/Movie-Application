package com.hcmus.movieapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.activities.LoginActivity;

public class LoggedOutFragment extends BaseFragment implements OnClickListener {

    private ImageView imgAvatar;

    public LoggedOutFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
