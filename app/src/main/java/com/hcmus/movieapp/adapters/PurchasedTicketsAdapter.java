package com.hcmus.movieapp.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.activities.PurchasedTicketDetailsActivity;
import com.hcmus.movieapp.models.Booking;
import com.hcmus.movieapp.utils.Constant;
import com.hcmus.movieapp.utils.Utilities;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PurchasedTicketsAdapter extends RecyclerView.Adapter<PurchasedTicketsAdapter.MyViewHolder>{
    private Context context;
    private List<Booking> bookingList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View view) {
            super(view);
        }
    }

    public class MovieTicketViewHolder extends MyViewHolder {
        public TextView movie, cinema, rated, date, time, room, runningTime, movieType, dash;
        public ImageView portrait, imgStatus;

        public MovieTicketViewHolder(View view) {
            super(view);
            movie = view.findViewById(R.id.txtMovie);
            cinema = view.findViewById(R.id.txtCinema);
            rated = view.findViewById(R.id.txtRated);
            date = view.findViewById(R.id.txtDate);
            time = view.findViewById(R.id.txtTime);
            room = view.findViewById(R.id.txtRoom);
            runningTime = view.findViewById(R.id.txtRunningTime);
            movieType = view.findViewById(R.id.txtMovieType);
            portrait  = view.findViewById(R.id.imgPortrait);
            imgStatus = view.findViewById(R.id.imgStatus);
            dash = view.findViewById(R.id.dash);
        }
    }

    public class EventTicketViewHolder extends MyViewHolder {
        public TextView event, venue, releaseDate, time, gateway, block, eventCategory, dash1, dash2;
        public ImageView landscape, imgStatus;

        public EventTicketViewHolder(View view) {
            super(view);
            landscape  = view.findViewById(R.id.imgLandscape);
            event = view.findViewById(R.id.txtEvent);
            venue = view.findViewById(R.id.txtVenue);
            releaseDate = view.findViewById(R.id.txtDate);
            time = view.findViewById(R.id.txtTime);
            gateway = view.findViewById(R.id.txtGateway);
            block = view.findViewById(R.id.txtBlock);
            eventCategory = view.findViewById(R.id.txtEventCategory);
            dash1 = view.findViewById(R.id.dash_1);
            dash2 = view.findViewById(R.id.dash_2);
            imgStatus = view.findViewById(R.id.imgStatus);
        }
    }

    public PurchasedTicketsAdapter(Context context, List<Booking> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case Constant.TicketType.MOVIE:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchased_movie_ticket_item, parent, false);
                return new MovieTicketViewHolder(itemView);
            case Constant.TicketType.EVENT:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchased_event_ticket_item, parent, false);
                return new EventTicketViewHolder(itemView);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {
        Booking item = bookingList.get(position);

        switch (viewHolder.getItemViewType()) {
            case Constant.TicketType.MOVIE: {
                MovieTicketViewHolder movieTicketViewHolder = (MovieTicketViewHolder) viewHolder;

                movieTicketViewHolder.movie.setText(item.getMovieName());
                movieTicketViewHolder.cinema.setText(item.getCinemaName());
                movieTicketViewHolder.rated.setText("C" + item.getMinAge());
                movieTicketViewHolder.runningTime.setText(item.getRunningTime());
                movieTicketViewHolder.movieType.setText(item.getMovieType());
                movieTicketViewHolder.date.setText(Utilities.convertDate(item.getReleaseDate()));
                movieTicketViewHolder.time.setText(Utilities.formatTime(item.getTime()));
                movieTicketViewHolder.room.setText(item.getRoom());

                Picasso.get().load(item.getImgURL()).error(R.drawable.poster).into(movieTicketViewHolder.portrait);

                movieTicketViewHolder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(context, PurchasedTicketDetailsActivity.class);
                    int pos = movieTicketViewHolder.getAdapterPosition();
                    intent.putExtra("purchased_ticket_info", bookingList.get(pos));
                    context.startActivity(intent);
                });

                if (item.isExpired() == true) {
                    movieTicketViewHolder.imgStatus.setVisibility(View.VISIBLE);
                    viewHolder.itemView.setEnabled(false);
                    movieTicketViewHolder.movie.setTextColor(ContextCompat.getColor(context, R.color.colorItemDisabled));
                    movieTicketViewHolder.cinema.setTextColor(ContextCompat.getColor(context, R.color.colorItemDisabled));
                    movieTicketViewHolder.runningTime.setTextColor(ContextCompat.getColor(context, R.color.colorItemDisabled));
                    movieTicketViewHolder.movieType.setTextColor(ContextCompat.getColor(context, R.color.colorItemDisabled));
                    movieTicketViewHolder.date.setTextColor(ContextCompat.getColor(context, R.color.colorItemDisabled));
                    movieTicketViewHolder.time.setTextColor(ContextCompat.getColor(context, R.color.colorItemDisabled));
                    movieTicketViewHolder.room.setTextColor(ContextCompat.getColor(context, R.color.colorItemDisabled));
                    movieTicketViewHolder.rated.setBackground(ContextCompat.getDrawable(context, R.drawable.border_background_dark_grey_color_no_padding));
                    movieTicketViewHolder.dash.setTextColor(ContextCompat.getColor(context, R.color.colorItemDisabled));
                }

                break;
            }
            case Constant.TicketType.EVENT: {
                EventTicketViewHolder eventTicketViewHolder = (EventTicketViewHolder) viewHolder;

                eventTicketViewHolder.event.setText(item.getEventName());
                eventTicketViewHolder.venue.setText(item.getVenue());
                eventTicketViewHolder.releaseDate.setText(Utilities.convertDate(item.getReleaseDate()));
                eventTicketViewHolder.time.setText(Utilities.formatTime(item.getTime()));
                eventTicketViewHolder.gateway.setText(item.getGateway());
                eventTicketViewHolder.block.setText(item.getBlock());

                switch (item.getEventCategory()) {
                    case 0: // Sport
                        eventTicketViewHolder.eventCategory.setText("Thể thao");
                        break;
                }

                Picasso.get().load(item.getImgURL()).error(R.drawable.poster).into(eventTicketViewHolder.landscape);

                eventTicketViewHolder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(context, PurchasedTicketDetailsActivity.class);
                    int pos = eventTicketViewHolder.getAdapterPosition();
                    intent.putExtra("purchased_ticket_info", bookingList.get(pos));
                    context.startActivity(intent);
                });

                if (item.isExpired() == true) {
                    eventTicketViewHolder.imgStatus.setVisibility(View.VISIBLE);
                    viewHolder.itemView.setEnabled(false);
                    eventTicketViewHolder.event.setTextColor(ContextCompat.getColor(context, R.color.colorItemDisabled));
                    eventTicketViewHolder.venue.setTextColor(ContextCompat.getColor(context, R.color.colorItemDisabled));
                    eventTicketViewHolder.releaseDate.setTextColor(ContextCompat.getColor(context, R.color.colorItemDisabled));
                    eventTicketViewHolder.time.setTextColor(ContextCompat.getColor(context, R.color.colorItemDisabled));
                    eventTicketViewHolder.gateway.setTextColor(ContextCompat.getColor(context, R.color.colorItemDisabled));
                    eventTicketViewHolder.block.setTextColor(ContextCompat.getColor(context, R.color.colorItemDisabled));
                    eventTicketViewHolder.eventCategory.setTextColor(ContextCompat.getColor(context, R.color.colorItemDisabled));
                    eventTicketViewHolder.dash1.setTextColor(ContextCompat.getColor(context, R.color.colorItemDisabled));
                    eventTicketViewHolder.dash2.setTextColor(ContextCompat.getColor(context, R.color.colorItemDisabled));
                }

                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Booking item = bookingList.get(position);
        return item.getType();
    }

    public void setBookingsByAccount(List<Booking> bookings) {
        this.bookingList = bookings;
        this.notifyDataSetChanged();
    }
}
