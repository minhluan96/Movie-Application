package com.example.movieapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.movieapp.R;
import com.example.movieapp.activities.RegistrationActivity;

public class AccountFragment extends BaseFragment implements OnClickListener {

    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    private Button btnRegister;

    public  AccountFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.account_fragment, container, false);
        edtUsername = v.findViewById(R.id.edtUsername);
        edtPassword = v.findViewById(R.id.edtPassword);
        btnLogin = v.findViewById(R.id.btnLogin);
        btnRegister = v.findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegister) {
            //create an Intent to talk to activity: RegistrationActivity
            Intent callRegistration = new Intent(getActivity(), RegistrationActivity.class);
            startActivity(callRegistration);
        }
        if (v.getId() == R.id.btnLogin) {

        }
    }
}
