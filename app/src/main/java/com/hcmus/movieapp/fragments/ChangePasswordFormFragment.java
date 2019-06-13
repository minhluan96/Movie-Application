package com.hcmus.movieapp.fragments;

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
import com.hcmus.movieapp.models.Account;
import com.hcmus.movieapp.utils.AppManager;
import com.hcmus.movieapp.utils.DataParser;
import com.hcmus.movieapp.utils.SaveSharedPreference;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordFormFragment extends BaseFragment implements OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private EditText edtCurrentPassword;
    private EditText edtNewPassword;
    private EditText edtRetype;
    private Button btnChangePassword;
    private static final String TAG_CHANGE_PASSWORD = "TAG_CHANGE_PASSWORD";

    public  ChangePasswordFormFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.change_password_form_fragment, container, false);

        tabLayout = v.findViewById(R.id.tabs);
        viewPager = v.findViewById(R.id.viewpager);
        toolbar = v.findViewById(R.id.toolbar);

        edtCurrentPassword = v.findViewById(R.id.edtCurrentPassword);
        edtNewPassword = v.findViewById(R.id.edtNewPassword);
        edtRetype = v.findViewById(R.id.edtRetype);
        btnChangePassword = v.findViewById(R.id.btnChangePassword);

        btnChangePassword.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnChangePassword) {
            String currentPassword = edtCurrentPassword.getText().toString();
            String newPassword = edtNewPassword.getText().toString();
            String retype = edtRetype.getText().toString();

            // validate input
            if (TextUtils.isEmpty(currentPassword)) {
                Toast.makeText(getContext(), "Vui lòng điền mật khẩu hiện tại", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(newPassword)) {
                Toast.makeText(getContext(), "Vui lòng điền mật khẩu mới", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(retype)) {
                Toast.makeText(getContext(), "Vui lòng điền mật khẩu xác nhận", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!newPassword.equals(retype)) {
                Toast.makeText(getContext(), "Mật khẩu mới và mật khẩu xác nhận không trùng khớp", Toast.LENGTH_SHORT).show();
                return;
            }
            changePassword();
        }
    }

    private void changePassword() {
        Account accountInfo = SaveSharedPreference.getAccountInfo(getContext());
        int id = accountInfo.getId();
        JSONObject body = new JSONObject();
        try {
            body.put("id", id);
            body.put("current_password", edtCurrentPassword.getText().toString());
            body.put("new_password", edtNewPassword.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppManager.getInstance().getCommService().doChangePassword(TAG_CHANGE_PASSWORD, body,
                new DataParser.DataResponseListener<JSONObject>() {
                    @Override
                    public void onDataResponse(JSONObject response) {
                        Toast.makeText(getContext(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();

                        edtCurrentPassword.getText().clear();
                        edtNewPassword.getText().clear();
                        edtRetype.getText().clear();
                    }

                    @Override
                    public void onDataError(String errorMessage) {

                    }

                    @Override
                    public void onRequestError(String errorMessage, VolleyError volleyError) {
                        Log.e("API-Account/ChangePwd", errorMessage);
                        if (volleyError.networkResponse.statusCode == HttpStatus.SC_BAD_REQUEST) {
                            Toast.makeText(getContext(), "Mật khẩu hiện tại không chính xác", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Thay đổi mật khẩu thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }
}

