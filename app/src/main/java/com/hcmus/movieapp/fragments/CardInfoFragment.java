package com.hcmus.movieapp.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.activities.ConfirmationActivity;
import com.hcmus.movieapp.activities.ConfirmationSportActivity;
import com.hcmus.movieapp.adapters.BankListAdapter;
import com.hcmus.movieapp.models.Bank;
import com.hcmus.movieapp.models.SeatMo;
import com.hcmus.movieapp.models.Ticket;
import com.hcmus.movieapp.utils.Constant;
import com.hcmus.movieapp.utils.Utilities;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
    private int type;
    private int eventType;


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
        if (type == 1) {
            setupDummyBankList();
        } else {
            setupDummyVisaCard();
        }
        setupAdapter();
        setupDatePicker();
        setUIData();

        btnConfirm.setOnClickListener(v1 -> {
            if (etCardNumber.getText().toString().isEmpty() || etCardOwner.getText().toString().isEmpty() || etExpiration.getText().toString().isEmpty()) {
                if (eventType == Constant.EventType.MOVIE) {
                    ((ConfirmationActivity) getActivity()).showDialogErrorWithOKButton(getActivity(), "Lỗi", "Vui lòng nhập đầy đủ các thông tin thanh toán");
                } else {
                    ((ConfirmationSportActivity) getActivity()).showDialogErrorWithOKButton(getActivity(), "Lỗi", "Vui lòng nhập đầy đủ các thông tin thanh toán");
                }
                return;

            }
            listener.onFinishInputCardHolder(etCardOwner.getText().toString(), etCardNumber.getText().toString(), etExpiration.getText().toString());
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventType == Constant.EventType.MOVIE) {
                    ((ConfirmationActivity) getActivity()).removeCardInfoFragment();
                } else {
                    ((ConfirmationSportActivity) getActivity()).removeCardInfoFragment();
                }
            }
        });

        return v;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setListener(CardInfoListener listener) {
        this.listener = listener;
    }

    private void setUIData() {
        if (eventType == Constant.EventType.MOVIE) {
            setupUIMovieData();
        } else {
            setupUISportData();
        }
    }

    private void setupUISportData() {
        String infoPayment = "";
        infoPayment += ((ConfirmationSportActivity) getActivity()).getStadium().getName() + " - ";
        infoPayment += ((ConfirmationSportActivity) getActivity()).getSport().getName() + " - ";
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

    private void setupUIMovieData() {
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
        etExpiration.setText((selectedMonth + 1) + "/" + selectedYear);
    }

    public boolean isBankListDisplay() {
        return bankListContainer.getVisibility() == View.VISIBLE;
    }

    private void setupDummyBankList() {
        bankList = new ArrayList<>();
        bankList.add(new Bank(1, "ACB", R.drawable.acb));
        bankList.add(new Bank(2, "Vietcombank", R.drawable.vietcombank));
        bankList.add(new Bank(3, "Sacombank", R.drawable.sacombank));
        bankList.add(new Bank(4, "HSBC", R.drawable.hsbc));
        bankList.add(new Bank(5, "DongA Bank", R.drawable.dongabank));
        bankList.add(new Bank(6, "Techcombank", R.drawable.techcombank));
    }

    private void setupDummyVisaCard() {
        bankList = new ArrayList<>();
        bankList.add(new Bank(1, "Visa", R.drawable.visa));
        bankList.add(new Bank(2, "Master", R.drawable.mastercard));
        bankList.add(new Bank(3, "JSB", R.drawable.jcb));
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

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
}
