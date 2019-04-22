package com.example.movieapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.activities.HomeActivity;
import com.example.movieapp.activities.LoginActivity;
import com.example.movieapp.utils.SaveSharedPreference;

public class NoTicketInfoFragment extends BaseFragment implements OnClickListener {

    private Button btnDirection;
    private TextView txtMessage;
    private Class classActivity;

    public NoTicketInfoFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.no_ticket_info, container, false);

        btnDirection = v.findViewById(R.id.btnDirection);
        txtMessage = v.findViewById(R.id.txtMessage);

        btnDirection.setOnClickListener(this);

        // If account is already logged in
        if(SaveSharedPreference.getLoggedStatus(getContext())) {
            txtMessage.setText("Chưa có giao dịch nào trên TicketNow");
            btnDirection.setText("Đặt vé ngay bạn nhé!");
            classActivity = HomeActivity.class;
        }
        else {
            // TODO: check this account have anny ticket in history system
            txtMessage.setText("Bạn cần đăng nhập để sử dụng tính năng này");
            btnDirection.setText("Đăng nhập");
            classActivity = LoginActivity.class;
        }
        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDirection) {
            //create an Intent to talk to activity: LoginActivity or HomeActivity
            Intent callActivity = new Intent(getActivity(), classActivity);
            startActivity(callActivity);
        }
    }
}
