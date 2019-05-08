package com.example.movieapp.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.movieapp.R;
import com.example.movieapp.models.BookedCombo;
import com.example.movieapp.models.Booking;
import com.example.movieapp.utils.AppManager;
import com.example.movieapp.utils.DataParser;
import com.squareup.picasso.Picasso;

import static com.example.movieapp.utils.Utilities.convertDate;
import static com.example.movieapp.utils.Utilities.formatTime;

import net.glxn.qrgen.android.QRCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PurchasedTicketInfoFragment extends BaseFragment {

    private ViewPager viewPager;
    private Toolbar toolbar;
    private TextView txtTitle, txtMinAge, txtDescription, txtCinema, txtAddress, txtDate, txtTime, txtRoom,
            txtSeatPlaces, txtCombos, txtCode;
    private ImageView cinemaIcon, imgQRCode;

    private Booking bookingInfo;
    private List<BookedCombo> bookedComboList;

    private static final String TAG_BOOKING_BOOKED_COMBOS = "TAG_BOOKING_BOOKED_COMBOS";

    public PurchasedTicketInfoFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.purchased_ticket_info_fragment, container, false);

        bookedComboList = new ArrayList<>();

        viewPager = v.findViewById(R.id.viewpager);
        toolbar = v.findViewById(R.id.toolbar);
        txtTitle = v.findViewById(R.id.txtTitle);
        txtMinAge = v.findViewById(R.id.txtMinAge);
        txtDescription = v.findViewById(R.id.txtDescription);
        txtCinema = v.findViewById(R.id.txtCinema);
        txtAddress = v.findViewById(R.id.txtAddress);
        cinemaIcon = v.findViewById(R.id.cinemaIcon);
        txtDate = v.findViewById(R.id.txtDate);
        txtTime = v.findViewById(R.id.txtTime);
        txtRoom = v.findViewById(R.id.txtRoom);
        txtSeatPlaces = v.findViewById(R.id.txtSeatPlaces);
        txtCombos = v.findViewById(R.id.txtCombos);
        txtCode = v.findViewById(R.id.txtCode);
        imgQRCode = v.findViewById(R.id.imgQRCode);

        bookingInfo = getArguments().getParcelable("purchased_ticket_info");

        txtTitle.setText(bookingInfo.getMovieName());
        txtMinAge.setText("C" + bookingInfo.getMinAge());
        txtDescription.setText(bookingInfo.getRunningTime() + " - " + bookingInfo.getType());
        txtCinema.setText(bookingInfo.getCinemaName());
        txtAddress.setText(bookingInfo.getAddress());
        Picasso.get().load(bookingInfo.getIconURL()).error(R.drawable.purchased_tickets).into(cinemaIcon);
        txtDate.setText(convertDate(bookingInfo.getReleaseDate()));
        txtTime.setText(formatTime(bookingInfo.getTime()));
        txtRoom.setText(bookingInfo.getRoom().replace("Phòng chiếu ", ""));
        // get seat places
        String seats = "";
        for (int i = 0; i < bookingInfo.getBookedSeatList().size(); i++) {
            seats += (bookingInfo.getBookedSeatList().get(i).getRow() + bookingInfo.getBookedSeatList().get(i).getNumber() + "  ");
        }
        txtSeatPlaces.setText(seats);
        txtCode.setText(bookingInfo.getCode());
        prepareQRCode();

        getAllBookedCombosByBooking(bookingInfo.getId());

        return v;
    }

    private void prepareQRCode() {
        Bitmap bitmap = QRCode.from(bookingInfo.getCode()).bitmap();
        imgQRCode.setImageBitmap(bitmap);
    }

    private void getAllBookedCombosByBooking(int id) {
    String bookingID = String.valueOf(id);
    AppManager.getInstance().getCommService().getBookedCombosByBooking(TAG_BOOKING_BOOKED_COMBOS, bookingID,
            new DataParser.DataResponseListener<LinkedList<BookedCombo>>() {
                @Override
                public void onDataResponse(LinkedList<BookedCombo> response) {
                    bookedComboList = response;

                    // get combos
                    String combos = "";
                    for (int i = 0; i < bookedComboList.size(); i++) {
                        if (i == (bookedComboList.size() - 1)) {
                            combos += (bookedComboList.get(i).getCombo());
                        }
                        else {
                            combos += (bookedComboList.get(i).getCombo() + "\n");
                        }
                    }
                    txtCombos.setText(combos);
                }

                @Override
                public void onDataError(String errorMessage) {
                }

                @Override
                public void onRequestError(String errorMessage, VolleyError volleyError) {
                    Log.e("API-Booking/BookedCombos", errorMessage);
                    Toast.makeText(getContext(), "Máy chủ bị lỗi! Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {

                }
            });
    }
}
