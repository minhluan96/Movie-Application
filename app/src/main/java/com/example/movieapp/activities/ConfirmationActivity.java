package com.example.movieapp.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.adapters.PaymentMethodAdapter;
import com.example.movieapp.fragments.CardInfoFragment;
import com.example.movieapp.models.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

public class ConfirmationActivity extends BaseActivity implements PaymentMethodAdapter.PaymentMethodListener {

    private RecyclerView rvPaymentMethods;
    private PaymentMethodAdapter paymentMethodAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView txtContinue, txtTotalPrice;

    private CardInfoFragment cardInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        rvPaymentMethods = findViewById(R.id.rv_paymentMethod);

        layoutManager = new GridLayoutManager(this, 3);
        rvPaymentMethods.setLayoutManager(layoutManager);

        txtContinue = findViewById(R.id.txtContinue);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);

        txtContinue.setOnClickListener(v -> {
            Intent intent = new Intent(ConfirmationActivity.this, ResultTicketActivity.class);
            startActivity(intent);
        });
        setupPaymentMethodAdapter();
    }

    private void setupPaymentMethodAdapter() {
        List<PaymentMethod> paymentMethods = setDummyDataPaymentMethod();
        paymentMethodAdapter = new PaymentMethodAdapter(paymentMethods, this);
        rvPaymentMethods.setAdapter(paymentMethodAdapter);
        paymentMethodAdapter.setListener(this);
    }

    private List<PaymentMethod> setDummyDataPaymentMethod() {
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        paymentMethods.add(new PaymentMethod(1, "Momo", "https://avatars3.githubusercontent.com/u/36770798?s=400&v=4"));
        paymentMethods.add(new PaymentMethod(2, "Visa/Master", ""));
        paymentMethods.add(new PaymentMethod(3, "Thẻ ATM nội địa", ""));
        return paymentMethods;
    }

    @Override
    public void onMethodSelected(int pos) {
        cardInfoFragment = new CardInfoFragment();
        showFragmentWithCustomAnimation(cardInfoFragment, R.id.container_fragment, R.anim.slide_in_up, R.anim.slide_out_up);
    }

    public void removeCardInfoFragment() {
        hideFragment(cardInfoFragment);
    }

    @Override
    public void onBackPressed() {
        if (cardInfoFragment.isVisible()) {
            if (cardInfoFragment.isBankListDisplay()) {
                hideFragment(cardInfoFragment);
            } else {
                cardInfoFragment.showCardInfoContainer(false);
            }
        } else {
            super.onBackPressed();
        }
    }
}
