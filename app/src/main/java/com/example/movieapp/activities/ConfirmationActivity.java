package com.example.movieapp.activities;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.adapters.PaymentMethodAdapter;
import com.example.movieapp.models.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

public class ConfirmationActivity extends BaseActivity {

    private RecyclerView rvPaymentMethods;
    private PaymentMethodAdapter paymentMethodAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        rvPaymentMethods = findViewById(R.id.rv_paymentMethod);

        layoutManager = new GridLayoutManager(this, 3);
        rvPaymentMethods.setLayoutManager(layoutManager);
        setupPaymentMethodAdapter();
    }

    private void setupPaymentMethodAdapter() {
        List<PaymentMethod> paymentMethods = setDummyDataPaymentMethod();
        paymentMethodAdapter = new PaymentMethodAdapter(paymentMethods, this);
        rvPaymentMethods.setAdapter(paymentMethodAdapter);
    }

    private List<PaymentMethod> setDummyDataPaymentMethod() {
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        paymentMethods.add(new PaymentMethod(1, "Momo", "https://avatars3.githubusercontent.com/u/36770798?s=400&v=4"));
        paymentMethods.add(new PaymentMethod(2, "Visa/Master", ""));
        paymentMethods.add(new PaymentMethod(3, "Thẻ ATM nội địa", ""));
        return paymentMethods;
    }
}
