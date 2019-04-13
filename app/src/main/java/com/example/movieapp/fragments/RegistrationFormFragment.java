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
import android.widget.EditText;

import com.example.movieapp.R;
import com.example.movieapp.activities.LoginActivity;

public class RegistrationFormFragment extends BaseFragment implements OnClickListener {

    private EditText txtFullName, txtUsername, txtPassword, txtRetype, txtPhone, txtEmail;
    private Button btnDirectToLogin;

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
        btnDirectToLogin = v.findViewById(R.id.btnDirectToLogin);

        btnDirectToLogin.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDirectToLogin) {
            //create an Intent to talk to activity: LoginActivity
            Intent callLogin = new Intent(getActivity(), LoginActivity.class);
            startActivity(callLogin);
        }
    }
}
