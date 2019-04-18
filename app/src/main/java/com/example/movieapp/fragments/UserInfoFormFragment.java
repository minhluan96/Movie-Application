package com.example.movieapp.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.RadioButton;

import com.example.movieapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
            updateUserInfo();
        }
        if (v.getId() == R.id.edtBirthday) {
            new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    private void updateUserInfo() {
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtBirthday.setText(sdf.format(myCalendar.getTime()));
    }
}
