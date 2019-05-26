package com.hcmus.movieapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.activities.HomeActivity;
import com.hcmus.movieapp.activities.LoginActivity;
import com.hcmus.movieapp.models.Account;
import com.hcmus.movieapp.models.User;
import com.hcmus.movieapp.utils.AppManager;
import com.hcmus.movieapp.utils.DataParser;
import com.hcmus.movieapp.utils.SaveSharedPreference;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static com.hcmus.movieapp.utils.Utilities.validateEmail;

public class RegistrationFormFragment extends BaseFragment implements OnClickListener {

    private EditText edtFullName, edtUsername, edtPassword, edtRetype, edtPhone, edtEmail;
    private Button btnDirectToLogin;
    private CheckBox chkAgreeToTerms;
    private Button btnSignUp;
    private static final String TAG_REGISTER = "TAG_REGISTER";

    public RegistrationFormFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.registration_form_fragment, container, false);
        edtFullName = v.findViewById(R.id.edtFullName);
        edtUsername = v.findViewById(R.id.edtUsername);
        edtPassword = v.findViewById(R.id.edtPassword);
        edtRetype = v.findViewById(R.id.edtRetype);
        edtPhone = v.findViewById(R.id.edtPhone);
        edtEmail = v.findViewById(R.id.edtEmail);
        chkAgreeToTerms = v.findViewById(R.id.chkAgreeToTerms);
        btnDirectToLogin = v.findViewById(R.id.btnDirectToLogin);
        btnSignUp = v.findViewById(R.id.btnSignUp);

        btnDirectToLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDirectToLogin) {
            //create an Intent to talk to activity: LoginActivity
            Intent callLogin = new Intent(getActivity(), LoginActivity.class);
            startActivity(callLogin);
        }
        if (v.getId() == R.id.btnSignUp) {
            String fullName = edtFullName.getText().toString();
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();
            String retype = edtRetype.getText().toString();
            String phone = edtPhone.getText().toString();
            String email = edtEmail.getText().toString();

            // validate input
            if (TextUtils.isEmpty(fullName)) {
                Toast.makeText(getContext(), "Vui lòng điền họ tên", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(getContext(), "Vui lòng điền tên đăng nhập", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Vui lòng điền mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(retype)) {
                Toast.makeText(getContext(), "Vui lòng điền mật khẩu xác nhận", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(getContext(), "Vui lòng điền số điện thoại", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getContext(), "Vui lòng điền địa chỉ email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(retype)) {
                Toast.makeText(getContext(), "Mật khẩu và mật khẩu xác nhận không trùng khớp", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!phone.matches("[0-9]+")) {
                Toast.makeText(getContext(), "Số điện thoại phải là chữ số", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!validateEmail(email)) {
                Toast.makeText(getContext(), "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!chkAgreeToTerms.isChecked()) {
                Toast.makeText(getContext(), "Vui lòng xác nhận đồng ý với các điều khoản dịch vụ", Toast.LENGTH_SHORT).show();
                return;
            }
            signUp();
        }
    }

    private void signUp() {
        JSONObject body = new JSONObject();
        try {
            body.put("username", edtUsername.getText().toString());
            body.put("password", edtPassword.getText().toString());
            body.put("full_name", edtFullName.getText().toString());
            body.put("phone", edtPhone.getText().toString());
            body.put("email", edtEmail.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppManager.getInstance().getCommService().doRegister(TAG_REGISTER, body,
                new DataParser.DataResponseListener<LinkedList<Account>>() {
                    @Override
                    public void onDataResponse(LinkedList<Account> response) {
                        String fullName = edtFullName.getText().toString();
                        String email = edtEmail.getText().toString();
                        String phone = edtPhone.getText().toString();

                        User userInfo = new User(fullName, null, email, phone, null, null);
                        Account accountInfo = new Account();
                        accountInfo.setId(response.get(0).getId());
                        accountInfo.setUser(userInfo);

                        // Set logged in status to 'true'
                        SaveSharedPreference.setLoggedIn(getContext(), true);

                        // Set user info
                        SaveSharedPreference.setAccountInfo(getContext(), accountInfo);

                        // Create an Intent to talk to activity: HomeActivity
                        Intent callHome = new Intent(getActivity(), HomeActivity.class);
                        callHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK|FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(callHome);
                    }

                    @Override
                    public void onDataError(String errorMessage) {

                    }

                    @Override
                    public void onRequestError(String errorMessage, VolleyError volleyError) {
                        Log.e("API-Account/Register", errorMessage);
                        if (volleyError.networkResponse.statusCode == HttpStatus.SC_CONFLICT) {
                            Toast.makeText(getContext(), "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }
}
