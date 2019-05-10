package com.example.movieapp.fragments;

import android.app.DatePickerDialog;
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
import com.example.movieapp.activities.ConfirmationActivity;
import com.example.movieapp.adapters.BankListAdapter;
import com.example.movieapp.models.Bank;
import com.example.movieapp.models.Cinema;
import com.example.movieapp.models.SeatMo;
import com.example.movieapp.models.Ticket;
import com.example.movieapp.utils.Utilities;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CardInfoFragment extends BaseFragment implements BankListAdapter.BankListener {

    private TextView txtToolbarTitle, txtDescription;
    private View bankListContainer, cardInfoContainer;
    private RecyclerView rvBankList;
    private EditText etCardOwner, etCardNumber, etExpiration;
    private Button btnCancel, btnConfirm;

    private RecyclerView.LayoutManager layoutManager;
    private BankListAdapter adapter;
    private List<Bank> bankList;
    private Calendar calendar = Calendar.getInstance();
    private CardInfoListener listener;


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
        txtDescription = v.findViewById(R.id.txtDescription);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvBankList.setLayoutManager(layoutManager);
        setupDummyBankList();
        setupAdapter();
        setupDatePicker();
        setUIData();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCardNumber.getText().toString().isEmpty() || etCardOwner.getText().toString().isEmpty() || etExpiration.getText().toString().isEmpty()) {
                    ((ConfirmationActivity)getActivity()).showDialogErrorWithOKButton(getActivity(), "Lỗi", "Vui lòng nhập đầy đủ các thông tin thanh toán");
                    return;
                }
                listener.onFinishInputCardHolder(etCardOwner.getText().toString(), etCardNumber.getText().toString(), etExpiration.getText().toString());
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ConfirmationActivity) getActivity()).removeCardInfoFragment();
            }
        });

        return v;
    }

    public void setListener(CardInfoListener listener) {
        this.listener = listener;
    }

    private void setUIData() {
        String infoPayment = "";
        infoPayment += ((ConfirmationActivity) getActivity()).getCinema().getName() + " - ";
        infoPayment += ((ConfirmationActivity) getActivity()).getMovie().getName() + " - ";
        List<SeatMo> selectedSeat = ((ConfirmationActivity) getActivity()).getSelectedSeats();
        for (SeatMo seat : selectedSeat) {
            infoPayment += seat.seatName + " ";
        }
        Map<Ticket, Integer> map = ((ConfirmationActivity) getActivity()).getMap();
        double totalPrice = 0;
        for (Map.Entry<Ticket, Integer> entry : map.entrySet()) {
            totalPrice += entry.getKey().getPrice()  * entry.getValue();
        }
        infoPayment += "- " + Utilities.formatCurrency(totalPrice);
        txtDescription.setText(infoPayment);
    }



    private void setupDatePicker() {
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getActivity(), this::updateExpirationDate,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));

        etExpiration.setOnClickListener(v -> builder.setActivatedMonth(Calendar.JANUARY)
                .setMinYear(2018)
                .setActivatedYear(2019)
                .setMaxYear(2050)
                .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER).build().show());
    }

    private void updateExpirationDate(int selectedMonth, int selectedYear) {
        etExpiration.setText(selectedMonth + "/" + selectedYear);
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

    public interface CardInfoListener {
        void onFinishInputCardHolder(String name, String cardNumber, String expirationDate);
    }
}
