package com.example.movieapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.adapters.BankListAdapter;
import com.example.movieapp.models.Bank;

import java.util.ArrayList;
import java.util.List;

public class CardInfoFragment extends BaseFragment implements BankListAdapter.BankListener {

    private TextView txtToolbarTitle;
    private View bankListContainer, cardInfoContainer;
    private RecyclerView rvBankList;
    private EditText etCardOwner, etCardNumber, etExpiration;
    private Button btnCancel, btnConfirm;

    private RecyclerView.LayoutManager layoutManager;
    private BankListAdapter adapter;
    private List<Bank> bankList;

    public CardInfoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.card_info_fragment, container, false);
        rvBankList = v.findViewById(R.id.bank_list);
        txtToolbarTitle = v.findViewById(R.id.txtToolbarTitle);
        bankListContainer = v.findViewById(R.id.bank_list_container);
        cardInfoContainer = v.findViewById(R.id.info_card_container);
        etCardNumber = v.findViewById(R.id.etCardNumber);
        etCardOwner = v.findViewById(R.id.etCardOwner);
        etExpiration = v.findViewById(R.id.etCardExpiration);
        btnCancel = v.findViewById(R.id.btnCancel);
        btnConfirm = v.findViewById(R.id.btnConfirm);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvBankList.setLayoutManager(layoutManager);
        setupDummyBankList();
        setupAdapter();

        return v;
    }

    public boolean isBankListDisplay() {
        return bankListContainer.getVisibility() == View.VISIBLE;
    }

    private void setupDummyBankList() {
        bankList = new ArrayList<>();
        bankList.add(new Bank(1, "ACB", "https://hstatic.net/131/1000047131/10/2015/11-27/acb.jpg"));
        bankList.add(new Bank(2, "Vietcombank", "https://brasol.vn/public/uploads/1521190030-brasol.vn-logo-vietcombank-logo-vietcombank.jpg"));
        bankList.add(new Bank(3, "Sacombank", "https://cdn.itviec.com/employers/sacombank/logo/social/6a3Kfnacdtq8EfREiPUsWdSs/sacombank-logo.png"));
        bankList.add(new Bank(4, "HSBC", "https://www.bankingtech.com/files/2017/06/HSBC-Commercial-Banking.png"));
        bankList.add(new Bank(5, "DongA Bank", "https://qdtek.vn/upload/post/partner/lg/090617093944.jpg"));
        bankList.add(new Bank(6, "Techcombank", "https://cdn.itviec.com/employers/techcombank/logo/social/G2KPh7rcAfxKYZWawVF1vLAw/techcombank-logo.png"));
    }

    private void setupAdapter() {
        adapter = new BankListAdapter(bankList, getActivity());
        adapter.setBankListener(this);
        rvBankList.setAdapter(adapter);
    }

    public void showCardInfoContainer(boolean isShow) {
        bankListContainer.setVisibility(isShow ? View.GONE : View.VISIBLE);
        cardInfoContainer.setVisibility(isShow ? View.VISIBLE : View.GONE);
        configureTitleBar(isShow);
    }

    private void configureTitleBar(boolean isCardInfo) {
        if (isCardInfo) {
            txtToolbarTitle.setText("Thông tin thanh toán");
        } else {
            txtToolbarTitle.setText("Danh sách ngân hàng");
        }
    }

    @Override
    public void onBankItemSelected(int pos) {
        showCardInfoContainer(true);
    }
}
