package com.hcmus.movieapp.fragments;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.models.Account;
import com.hcmus.movieapp.models.User;
import com.hcmus.movieapp.utils.AppManager;
import com.hcmus.movieapp.utils.DataParser;
import com.hcmus.movieapp.utils.SaveSharedPreference;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Locale;

import static com.hcmus.movieapp.utils.Utilities.convertDate;
import static com.hcmus.movieapp.utils.Utilities.extractDateFromString;
import static com.hcmus.movieapp.utils.Utilities.validateEmail;

public class UserInfoFormFragment extends BaseFragment implements OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private EditText edtFullName;
    private RadioButton radMale;
    private RadioButton radFemale;
    private EditText edtBirthday;
    private EditText edtEmail;
    private EditText edtPhone;
    private EditText edtAddress;
    private Button btnUpdate;

    private final Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date;

    private static final String TAG_USER_INFO = "TAG_USER_INFO";
    private static final String TAG_USER_UPDATE = "TAG_USER_UPDATE";

    private Account accountInfo;

    public  UserInfoFormFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_info_form_fragment, container, false);

        tabLayout = v.findViewById(R.id.tabs);
        viewPager = v.findViewById(R.id.viewpager);
        toolbar = v.findViewById(R.id.toolbar);

        edtFullName = v.findViewById(R.id.edtFullName);
        radMale = v.findViewById(R.id.radMale);
        radFemale = v.findViewById(R.id.radFemale);
        edtBirthday = v.findViewById(R.id.edtBirthday);
        edtEmail = v.findViewById(R.id.edtEmail);
        edtPhone = v.findViewById(R.id.edtPhone);
        edtAddress = v.findViewById(R.id.edtAddress);
        btnUpdate = v.findViewById(R.id.btnUpdate);

        accountInfo = SaveSharedPreference.getAccountInfo(getContext());

        // get user info in server
        getUserInfo();

        btnUpdate.setOnClickListener(this);
        edtBirthday.setOnClickListener(this);

        date = new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnUpdate) {
            // validate input
            if (TextUtils.isEmpty(edtFullName.getText().toString())) {
                Toast.makeText(getContext(), "Họ tên không được rỗng", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                Toast.makeText(getContext(), "Số điện thoại không được rỗng", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(edtEmail.getText().toString())) {
                Toast.makeText(getContext(), "Email không được rỗng", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!edtPhone.getText().toString().matches("[0-9]+")) {
                Toast.makeText(getContext(), "Số điện thoại phải là chữ số", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!validateEmail(edtEmail.getText().toString())) {
                Toast.makeText(getContext(), "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
                return;
            }
            updateUserInfo();
        }
        if (v.getId() == R.id.edtBirthday) {
            if (edtBirthday.getText().toString() != null) {
                String str[] = extractDateFromString(edtBirthday.getText().toString());
                int day = Integer.parseInt(str[0]);
                int month = Integer.parseInt(str[1]);
                int year = Integer.parseInt(str[2]);

                new DatePickerDialog(getContext(), date, year, month, day).show();
            } else {
                new DatePickerDialog(getContext(), date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        }
    }

    private void updateUserInfo() {
        int id = accountInfo.getId();
        String fullName = edtFullName.getText().toString();
        Integer gender;
        if (radMale.isChecked())
            gender = 1;
        else if (radFemale.isChecked())
            gender = 0;
        else
            gender = null;
        String email = edtEmail.getText().toString();
        String phone = edtPhone.getText().toString();
        String address = edtAddress.getText().toString();
        String[] str = extractDateFromString(edtBirthday.getText().toString());
        String newBirthdayStr = str[2] + "-" + str[1] + "-" + str[0];

        JSONObject body = new JSONObject();
        try {
            body.put("id", id);
            body.put("full_name", fullName);
            if (gender != null)
                body.put("gender", gender);
            body.put("birthday", newBirthdayStr);
            body.put("email", email);
            body.put("phone", phone);
            body.put("address", address);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppManager.getInstance().getCommService().doUpdateUserInfo(TAG_USER_UPDATE, body,
                new DataParser.DataResponseListener<JSONObject>() {
                    @Override
                    public void onDataResponse(JSONObject response) {
                        Toast.makeText(getContext(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();

                        // update in SharedPreference
                        String newBirthdayStr = str[0] + "/" + str[1] + "/" + str[2];
                        User userInfo = new User(fullName, gender, email, phone, address, newBirthdayStr);
                        Account accountInfo = new Account();
                        accountInfo.setId(id);
                        accountInfo.setUser(userInfo);

                        SaveSharedPreference.setAccountInfo(getContext(), accountInfo);
                    }

                    @Override
                    public void onDataError(String errorMessage) {

                    }

                    @Override
                    public void onRequestError(String errorMessage, VolleyError volleyError) {
                        Log.e("API-User/Update", errorMessage);
                        if (volleyError.networkResponse.statusCode == HttpStatus.SC_BAD_REQUEST) {
                            Toast.makeText(getContext(), "Cập nhật thông tin thất bại!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Máy chủ bị lỗi! Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtBirthday.setText(sdf.format(myCalendar.getTime()));
    }

    private void getUserInfo() {
        String accountID = String.valueOf(accountInfo.getId());
        AppManager.getInstance().getCommService().getUserInfo(TAG_USER_INFO, accountID,
                new DataParser.DataResponseListener<LinkedList<User>>() {
                    @Override
                    public void onDataResponse(LinkedList<User> response) {
                        // setup user info for GUI
                        edtFullName.setText(response.get(0).getFullName());
                        if (response.get(0).getGender() == 1) {
                            radMale.setChecked(true);
                        } else if (response.get(0).getGender() == 0) {
                            radFemale.setChecked(true);
                        }
                        if (response.get(0).getBirthday() != null) {
                            edtBirthday.setText(convertDate(response.get(0).getBirthday()));
                        }
                        edtEmail.setText(response.get(0).getEmail());
                        edtPhone.setText(response.get(0).getPhone());
                        edtAddress.setText(response.get(0).getAddress());
                    }

                    @Override
                    public void onDataError(String errorMessage) {

                    }

                    @Override
                    public void onRequestError(String errorMessage, VolleyError volleyError) {
                        Log.e("API-User/Info", errorMessage);
                        if (volleyError.networkResponse.statusCode == HttpStatus.SC_NO_CONTENT) {
                            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Lấy thông tin dữ liệu thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }
}
