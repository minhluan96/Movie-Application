package com.example.movieapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.movieapp.R;

public class RegistrationFormFragment extends BaseFragment {

    private EditText txtFullName, txtUsername, txtPassword, txtRetype, txtPhone, txtEmail;

    public RegistrationFormFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.registration_form_fragment, container, false);
        txtFullName = v.findViewById(R.id.txtFullName);
        txtUsername = v.findViewById(R.id.txtUsername);
        txtPassword = v.findViewById(R.id.txtPassword);
        txtRetype = v.findViewById(R.id.txtRetype);
        txtPhone = v.findViewById(R.id.txtPhone);
        txtEmail = v.findViewById(R.id.txtEmail);
        return v;
    }
}
