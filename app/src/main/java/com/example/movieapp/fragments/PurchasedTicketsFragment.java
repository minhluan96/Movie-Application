package com.example.movieapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.movieapp.R;
import com.example.movieapp.activities.HomeActivity;
import com.example.movieapp.activities.LoginActivity;
import com.example.movieapp.adapters.PurchasedTicketsAdapter;
import com.example.movieapp.models.Account;
import com.example.movieapp.models.BookedCombo;
import com.example.movieapp.models.BookedSeat;
import com.example.movieapp.models.Booking;
import com.example.movieapp.utils.AppManager;
import com.example.movieapp.utils.DataParser;
import com.example.movieapp.utils.SaveSharedPreference;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PurchasedTicketsFragment extends BaseFragment implements OnClickListener  {

    private RecyclerView recyclerView;
    private List<Booking> bookingList;
    private List<BookedSeat> bookedSeatList;
    private List<BookedCombo> bookedComboList;
    private PurchasedTicketsAdapter purchasedTicketsAdapter;
    private RecyclerView.LayoutManager purchaseTicketsLayoutManager;

    private Button btnDirection;
    private TextView txtMessage;
    private Class classActivity;

    private View purchasedTickets, noPurchasedTicketsInfo;

    private Account accountInfo;

    private static final String TAG_PURCHASED_TICKETS = "TAG_PURCHASED_TICKETS";
    private static final String TAG_BOOKING_BOOKED_SEATS = "TAG_BOOKING_BOOKED_SEATS";
    private static final String TAG_BOOKING_BOOKED_COMBOS = "TAG_BOOKING_BOOKED_COMBOS";

    public PurchasedTicketsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.purchased_tickets_fragment, container, false);

        purchasedTickets = v.findViewById(R.id.purchasedTickets);
        noPurchasedTicketsInfo = v.findViewById(R.id.noPurchasedTicketsInfo);

        accountInfo = SaveSharedPreference.getAccountInfo(getContext());

        bookingList = new ArrayList<>();
        bookedSeatList = new ArrayList<>();
        bookedComboList = new ArrayList<>();

        purchasedTicketsAdapter = new PurchasedTicketsAdapter(getContext(), bookingList);

        recyclerView = v.findViewById(R.id.recycler_view);

        btnDirection = noPurchasedTicketsInfo.findViewById(R.id.btnDirection);
        txtMessage = noPurchasedTicketsInfo.findViewById(R.id.txtMessage);

        btnDirection.setOnClickListener(this);

        getAllBookingsByAccount();

        // If account is logged in
        if(SaveSharedPreference.getLoggedStatus(getContext())) {
            txtMessage.setText("Chưa có giao dịch nào trên TicketNow");
            btnDirection.setText("Đặt vé ngay bạn nhé!");
            classActivity = HomeActivity.class;
        }
        else {
            txtMessage.setText("Bạn cần đăng nhập để sử dụng tính năng này");
            btnDirection.setText("Đăng nhập");
            classActivity = LoginActivity.class;
        }

        purchaseTicketsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(purchaseTicketsLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(purchasedTicketsAdapter);

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDirection) {
            //create an Intent to talk to activity: LoginActivity or HomeActivity
            Intent callActivity = new Intent(getActivity(), classActivity);
            startActivity(callActivity);
        }
    }

    private void getAllBookingsByAccount() {
        String accountID = String.valueOf(accountInfo.getId());
        AppManager.getInstance().getCommService().getBookingsByAccount(TAG_PURCHASED_TICKETS, accountID,
                new DataParser.DataResponseListener<LinkedList<Booking>>() {
                    @Override
                    public void onDataResponse(LinkedList<Booking> response) {
                        bookingList = response;

//                        // get booked seats and booked combos info
//                        for (int i = 0; i < bookingList.size(); i++) {
//                            int bookingID = bookingList.get(i).getId();
//                            getAllBookedSeatsByBooking(bookingID);
//                            getAllBookedCombosByBooking(bookingID);
//
//                            bookingList.get(i).setBookedSeatList(new ArrayList<>());
//                            bookingList.get(i).setBookedComboList(new ArrayList<>());
//
//                            bookingList.get(i).setBookedSeatList(bookedSeatList);
//                            bookingList.get(i).setBookedComboList(bookedComboList);
//                        }

                        purchasedTicketsAdapter.setBookingsByAccount(bookingList);

                        // Show purchased tickets layout, hide no ticket info layout
                        if (purchasedTickets.getVisibility() == View.GONE) {
                            purchasedTickets.setVisibility(View.VISIBLE);
                            noPurchasedTicketsInfo.setVisibility(View.GONE);
                        }
                        else {
                            noPurchasedTicketsInfo.setVisibility(View.GONE);
                            purchasedTickets.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onDataError(String errorMessage) {
                        // Show no ticket info layout, hide purchased tickets layout
                        if (noPurchasedTicketsInfo.getVisibility() == View.GONE) {
                            noPurchasedTicketsInfo.setVisibility(View.VISIBLE);
                            purchasedTickets.setVisibility(View.GONE);
                        }
                        else {
                            purchasedTickets.setVisibility(View.GONE);
                            noPurchasedTicketsInfo.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onRequestError(String errorMessage, VolleyError volleyError) {
                        Log.e("API-PurchasedTickets", errorMessage);
                        Toast.makeText(getContext(), "Máy chủ bị lỗi! Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

//    private void getAllBookedSeatsByBooking(int id) {
//        String bookingID = String.valueOf(id);
//        AppManager.getInstance().getCommService().getBookedSeatsByBooking(TAG_BOOKING_BOOKED_SEATS, bookingID,
//                new DataParser.DataResponseListener<LinkedList<BookedSeat>>() {
//                    @Override
//                    public void onDataResponse(LinkedList<BookedSeat> response) {
//                        bookedSeatList = response;
//                    }
//
//                    @Override
//                    public void onDataError(String errorMessage) {
//                    }
//
//                    @Override
//                    public void onRequestError(String errorMessage, VolleyError volleyError) {
//                        Log.e("API-Booking/BookedSeats", errorMessage);
//                        Toast.makeText(getContext(), "Máy chủ bị lỗi! Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//                });
//    }
//
//    private void getAllBookedCombosByBooking(int id) {
//        String bookingID = String.valueOf(id);
//        AppManager.getInstance().getCommService().getBookedCombosByBooking(TAG_BOOKING_BOOKED_COMBOS, bookingID,
//                new DataParser.DataResponseListener<LinkedList<BookedCombo>>() {
//                    @Override
//                    public void onDataResponse(LinkedList<BookedCombo> response) {
//                        bookedComboList = response;
//                    }
//
//                    @Override
//                    public void onDataError(String errorMessage) {
//                    }
//
//                    @Override
//                    public void onRequestError(String errorMessage, VolleyError volleyError) {
//                        Log.e("API-Booking/BookedCombos", errorMessage);
//                        Toast.makeText(getContext(), "Máy chủ bị lỗi! Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//                });
//    }
}
