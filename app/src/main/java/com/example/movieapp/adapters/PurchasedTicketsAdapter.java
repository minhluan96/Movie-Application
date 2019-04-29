package com.example.movieapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.activities.PurchasedTicketDetailsActivity;
import com.example.movieapp.activities.SellingTicketActivity;
import com.example.movieapp.fragments.PurchasedTicketsFragment;
import com.example.movieapp.models.Booking;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.movieapp.utils.Utilities.convertDate;
import static com.example.movieapp.utils.Utilities.formatTime;

public class PurchasedTicketsAdapter extends RecyclerView.Adapter<PurchasedTicketsAdapter.MyViewHolder>{
    private Context context;
    private List<Booking> bookingList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView movie, cinema, rated, date, time, room, seats;
        public ImageView thumbnail;
        public RelativeLayout viewForeground;

        public MyViewHolder(View view) {
            super(view);
            movie = view.findViewById(R.id.txtMovie);
            cinema = view.findViewById(R.id.txtCinema);
            rated = view.findViewById(R.id.txtRated);
            date = view.findViewById(R.id.txtDate);
            time = view.findViewById(R.id.txtTime);
            room = view.findViewById(R.id.txtRoom);
            seats = view.findViewById(R.id.txtSeats);
            thumbnail = view.findViewById(R.id.imgThumbnail);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }

    public PurchasedTicketsAdapter(Context context, List<Booking> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchased_ticket_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {
        final Booking item = bookingList.get(position);
        viewHolder.movie.setText(item.getMovieName());
        viewHolder.cinema.setText(item.getCinemaName());
        viewHolder.rated.setText("C"+ item.getMinAge());
        viewHolder.date.setText(convertDate(item.getReleaseDate()));
        viewHolder.time.setText(formatTime(item.getTime()));
        viewHolder.room.setText(item.getRoom());

        // get seat places
        String seats = "";
        for (int i = 0; i < item.getBookedSeatList().size(); i++) {
            seats += (item.getBookedSeatList().get(i).getRow() + item.getBookedSeatList().get(i).getNumber() + "  ");
        }
        viewHolder.seats.setText(seats);

        Picasso.get().load(item.getImgURL()).error(R.drawable.poster).into(viewHolder.thumbnail);

        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PurchasedTicketDetailsActivity.class);
            int pos = viewHolder.getAdapterPosition();
            intent.putExtra("purchased_ticket_info", bookingList.get(pos));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public void setBookingsByAccount(List<Booking> bookings) {
        this.bookingList = bookings;
        this.notifyDataSetChanged();
    }
}
