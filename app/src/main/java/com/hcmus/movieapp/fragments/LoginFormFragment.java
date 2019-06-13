package com.hcmus.movieapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.activities.RegistrationActivity;
import com.hcmus.movieapp.models.Account;
import com.hcmus.movieapp.models.User;
import com.hcmus.movieapp.utils.AppManager;
import com.hcmus.movieapp.utils.DataParser;
import com.hcmus.movieapp.utils.SaveSharedPreference;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

import static com.hcmus.movieapp.utils.Utilities.convertDate;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
            String password = edtPassword.getText().toString();
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
        JSONObject body = new JSONObject();
        try {
            body.put("username", edtUsername.getText().toString());
            body.put("password", edtPassword.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppManager.getInstance().getCommService().doLogin(TAG_LOGIN, body,
                new DataParser.DataResponseListener<LinkedList<Account>>() {
                    @Override
                    public void onDataResponse(LinkedList<Account> response) {
                        int id = response.get(0).getId();
                        String username = response.get(0).getUsername();
                        String fullName = response.get(0).getUser().getFullName();
                        Integer gender = response.get(0).getUser().getGender();
                        String email = response.get(0).getUser().getEmail();
                        String phone = response.get(0).getUser().getPhone();
                        String address = response.get(0).getUser().getAddress();
                        String birthday = null;
                        if (response.get(0).getUser().getBirthday() != null) {
                            birthday = convertDate(response.get(0).getUser().getBirthday());
                        }

                        User userInfo = new User(fullName, gender, email, phone, address, birthday);
                        Account accountInfo = new Account(id, username, null, userInfo);

                        // Set logged in status to 'true'
                        SaveSharedPreference.setLoggedIn(getContext(), true);

                        // Set user info
                        SaveSharedPreference.setAccountInfo(getContext(), accountInfo);

                        // Go back personal fragment by call onBackPressed of parent activity
                        getActivity().onBackPressed();
                    }

                    @Override
                    public void onDataError(String errorMessage) {

                    }

                    @Override
                    public void onRequestError(String errorMessage, VolleyError volleyError) {
                        Log.e("API-Account/Login", errorMessage);
                        if (volleyError.networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                            Toast.makeText(getContext(), "Sai thông tin tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
        });
    }
}
