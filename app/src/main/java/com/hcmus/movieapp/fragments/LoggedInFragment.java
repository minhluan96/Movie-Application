package com.hcmus.movieapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.activities.ChangePasswordActivity;
import com.hcmus.movieapp.activities.HomeActivity;
import com.hcmus.movieapp.activities.UserInfoActivity;
import com.hcmus.movieapp.models.Account;
import com.hcmus.movieapp.utils.SaveSharedPreference;

public class LoggedInFragment extends BaseFragment implements OnClickListener {

    private Button btnChangePassword;
    private Button btnLogout;
    private ImageView imgAvatar;
    private TextView txtFullName;
    private TextView txtEmail;
    private TextView txtPhone;
    private TextView txtBirthday;

    public LoggedInFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.logged_in_fragment, container, false);

        btnChangePassword = v.findViewById(R.id.btnChangePassword);
        btnLogout = v.findViewById(R.id.btnLogout);
        imgAvatar = v.findViewById(R.id.imgAvatar);
        txtFullName = v.findViewById(R.id.txtFullName);
        txtEmail = v.findViewById(R.id.txtEmail);
        txtPhone = v.findViewById(R.id.txtPhone);
        txtBirthday = v.findViewById(R.id.txtBirthday);

        Account accountInfo = SaveSharedPreference.getAccountInfo(getContext());
        txtFullName.setText(accountInfo.getUser().getFullName());
        txtEmail.setText(accountInfo.getUser().getEmail());
        txtPhone.setText(accountInfo.getUser().getPhone());
        txtBirthday.setText(accountInfo.getUser().getBirthday());

        btnChangePassword.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        imgAvatar.setOnClickListener(this);
        txtFullName.setOnClickListener(this);
        txtPhone.setOnClickListener(this);
        txtBirthday.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnChangePassword.getId()) {
            // Create an Intent to talk to activity: ChangePasswordActivity
            Intent callChangePassword = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(callChangePassword);
        }
        if (v.getId() == btnLogout.getId()) {
            // Set LoggedIn status to false
            SaveSharedPreference.setLoggedIn(getContext(), false);

            // Remove user info
            SaveSharedPreference.removeUserInfo(getContext());

            // Reload personal fragment
            PersonalFragment frg = new PersonalFragment();
            ((HomeActivity)getActivity()).showFragment(frg, R.id.container_layout);
        }
        if (v.getId() == imgAvatar.getId() || v.getId() == txtFullName.getId() || v.getId() == txtPhone.getId() || v.getId() == txtBirthday.getId()) {
            // Create an Intent to talk to activity: UserInfoActivity
            Intent callUserInfo = new Intent(getActivity(), UserInfoActivity.class);
            startActivity(callUserInfo);
        }
    }
}
