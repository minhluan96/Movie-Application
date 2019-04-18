package com.example.movieapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieapp.R;
import com.example.movieapp.activities.RegistrationActivity;
import com.example.movieapp.models.User;
import com.example.movieapp.utils.SaveSharedPreference;

public class LoginFormFragment extends BaseFragment implements OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    private Button btnRegister;
    private static final String TAG_LOGIN = "TAG_LOGIN";

    public  LoginFormFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_form_fragment, container, false);

        tabLayout = v.findViewById(R.id.tabs);
        viewPager = v.findViewById(R.id.viewpager);
        toolbar = v.findViewById(R.id.toolbar);

        edtUsername = v.findViewById(R.id.edtUsername);
        edtPassword = v.findViewById(R.id.edtPassword);
        btnLogin = v.findViewById(R.id.btnLogin);
        btnRegister = v.findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

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
            String username = edtUsername.getText().toString();
            String password = edtUsername.getText().toString();
            // validate input
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(getContext(), "Vui lòng điền tên đăng nhập", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Vui lòng điền mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }
            login();
        }
    }

    private void login() {
//        AppManager.getInstance().getCommService().getLatestMovies(TAG_LOGIN, new DataParser.DataResponseListener<LinkedList<String>>() {
//            @Override
//            public void onDataResponse(String result) {
//
//                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onDataError(String errorMessage) {
//
//            }
//
//            @Override
//            public void onRequestError(String errorMessage, VolleyError volleyError) {
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        });

        User userInfo = new User("Châu Hải Hùng", 1, "haihung100495@gmail.com", "0915512337", "Tp. HCM", "10/04/1995");

        // Set logged in status to 'true'
        SaveSharedPreference.setLoggedIn(getContext(), true);

        // Set user info
        SaveSharedPreference.setUserInfo(getContext(), userInfo);

        // TODO: go back user info fragment
    }
}
