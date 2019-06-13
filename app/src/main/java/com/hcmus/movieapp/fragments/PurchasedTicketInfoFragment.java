package com.hcmus.movieapp.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.models.BookedCombo;
import com.hcmus.movieapp.models.Booking;
import com.hcmus.movieapp.utils.AppManager;
import com.hcmus.movieapp.utils.DataParser;
import com.hcmus.movieapp.utils.Constant;
import com.hcmus.movieapp.utils.Utilities;
import com.squareup.picasso.Picasso;

import net.glxn.qrgen.android.QRCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PurchasedTicketInfoFragment extends BaseFragment {

    private ViewPager viewPager;
    private Toolbar toolbar;
    private TextView txtTitle, txtSeatPlaces, txtCode, txtAddress,
            txtMinAge, txtDescription, txtCinema, txtDate, txtTime, txtRoom, txtCombos,
            txtCategory, txtOrganizer, txtVenue, txtGateway, txtBlocks, txtRows, txtNumbers;
    private ImageView cinemaIcon, unitIcon, imgQRCode;

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
        bookedComboList = new ArrayList<>();

        bookingInfo = getArguments().getParcelable("purchased_ticket_info");

        View v = null;

        switch (bookingInfo.getType()) {
            case Constant.TicketType.MOVIE: {
                v = inflater.inflate(R.layout.purchased_movie_ticket_info_fragment, container, false);
                initMovieTicketInfoLayout(v);
                break;
            }
            case Constant.TicketType.EVENT: {
                v = inflater.inflate(R.layout.purchased_event_ticket_info_fragment, container, false);
                initEventTicketInfoLayout(v);
                break;
            }
        }

        txtCode.setText(bookingInfo.getCode());
        prepareQRCode();

        return v;
    }

    private void initMovieTicketInfoLayout(View v) {
        viewPager = v.findViewById(R.id.viewpager);
        toolbar = v.findViewById(R.id.toolbar);
        txtSeatPlaces = v.findViewById(R.id.txtSeatPlaces);
        txtCombos = v.findViewById(R.id.txtCombos);
        txtCode = v.findViewById(R.id.txtCode);
        imgQRCode = v.findViewById(R.id.imgQRCode);
        txtTitle = v.findViewById(R.id.txtTitle);
        txtAddress = v.findViewById(R.id.txtAddress);
        txtMinAge = v.findViewById(R.id.txtMinAge);
        txtDescription = v.findViewById(R.id.txtDescription);
        txtCinema = v.findViewById(R.id.txtCinema);
        cinemaIcon = v.findViewById(R.id.cinemaIcon);
        txtDate = v.findViewById(R.id.txtDate);
        txtTime = v.findViewById(R.id.txtTime);
        txtRoom = v.findViewById(R.id.txtRoom);

        // Set values
        txtTitle.setText(bookingInfo.getMovieName());
        txtMinAge.setText("C" + bookingInfo.getMinAge());
        txtDescription.setText(bookingInfo.getRunningTime() + " - " + bookingInfo.getMovieType());
        txtCinema.setText(bookingInfo.getCinemaName());
        txtAddress.setText(bookingInfo.getAddress());
        Picasso.get().load(bookingInfo.getIconURL()).error(R.drawable.purchased_tickets).into(cinemaIcon);
        txtDate.setText(Utilities.convertDate(bookingInfo.getReleaseDate()));
        txtTime.setText(Utilities.formatTime(bookingInfo.getTime()));
        txtRoom.setText(bookingInfo.getRoom().replace("Rạp ", ""));

        getAllBookedCombosByBooking(bookingInfo.getId());

        // get seat places
        String seats = "";
        for (int i = 0; i < bookingInfo.getBookedSeatList().size(); i++) {
            seats += (bookingInfo.getBookedSeatList().get(i).getRow() + bookingInfo.getBookedSeatList().get(i).getNumber() + "  ");
        }
        // get seat places
        txtSeatPlaces.setText(seats);
    }

    private void initEventTicketInfoLayout(View v) {
        viewPager = v.findViewById(R.id.viewpager);
        toolbar = v.findViewById(R.id.toolbar);
        txtSeatPlaces = v.findViewById(R.id.txtSeatPlaces);
        txtCode = v.findViewById(R.id.txtCode);
        imgQRCode = v.findViewById(R.id.imgQRCode);
        txtTitle = v.findViewById(R.id.txtTitle);
        txtAddress = v.findViewById(R.id.txtAddress);
        txtCategory = v.findViewById(R.id.txtCategory);
        txtOrganizer = v.findViewById(R.id.txtOrganizer);
        txtVenue = v.findViewById(R.id.txtVenue);
        txtDate = v.findViewById(R.id.txtDate);
        txtTime = v.findViewById(R.id.txtTime);
        txtGateway = v.findViewById(R.id.txtGateway);
        txtBlocks = v.findViewById(R.id.txtBlocks);
        txtBlocks = v.findViewById(R.id.txtBlocks);
        txtRows = v.findViewById(R.id.txtRows);
        txtNumbers = v.findViewById(R.id.txtNumbers);
        unitIcon = v.findViewById(R.id.unitIcon);

        // Set values
        txtTitle.setText(bookingInfo.getEventName());
        switch (bookingInfo.getEventCategory()) {
            case 0: // Sport
                txtCategory.setText("Thể thao");
                break;
        }
        txtOrganizer.setText(bookingInfo.getOrganizer());
        txtVenue.setText(bookingInfo.getVenue());
        txtDate.setText(Utilities.convertDate(bookingInfo.getReleaseDate()));
        txtTime.setText(Utilities.formatTime(bookingInfo.getTime()));
        txtGateway.setText(bookingInfo.getGateway());
        txtBlocks.setText(bookingInfo.getBlock().replace("Khán đài ", ""));
        Picasso.get().load(bookingInfo.getIconURL()).error(R.drawable.purchased_tickets).into(unitIcon);

        // get rows, numbers
        String rows = "";
        String numbers = "";
        for (int i = 0; i < bookingInfo.getBookedSeatList().size(); i++) {
            if (i == (bookingInfo.getBookedSeatList().size() - 1)) {
                rows += (bookingInfo.getBookedSeatList().get(i).getRow());
                numbers += (bookingInfo.getBookedSeatList().get(i).getNumber());
            } else {
                rows += (bookingInfo.getBookedSeatList().get(i).getRow() + "\n");
                numbers += (bookingInfo.getBookedSeatList().get(i).getNumber() + "\n");
            }
        }
        // set rows, numbers
        txtRows.setText(rows);
        txtNumbers.setText(numbers);
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
