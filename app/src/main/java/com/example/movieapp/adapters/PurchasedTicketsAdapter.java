package com.example.movieapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.activities.PurchasedTicketDetailsActivity;
import com.example.movieapp.models.Booking;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.movieapp.utils.Constant.*;
import static com.example.movieapp.utils.Utilities.convertDate;
import static com.example.movieapp.utils.Utilities.formatTime;

public class PurchasedTicketsAdapter extends RecyclerView.Adapter<PurchasedTicketsAdapter.MyViewHolder>{
    private Context context;
    private List<Booking> bookingList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View view) {
            super(view);
        }
    }

    public class MovieTicketViewHolder extends MyViewHolder {
        public TextView movie, cinema, rated, date, time, room, runningTime, movieType;
        public ImageView portrait;

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
        }
    }

    public class EventTicketViewHolder extends MyViewHolder {
        public TextView event, venue, releaseDate, time, gateway, block, eventCategory;
        public ImageView landscape;

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
            case TicketType.MOVIE:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchased_movie_ticket_item, parent, false);
                return new MovieTicketViewHolder(itemView);
            case TicketType.EVENT:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchased_event_ticket_item, parent, false);
                return new EventTicketViewHolder(itemView);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {
        Booking item = bookingList.get(position);

        switch (viewHolder.getItemViewType()) {
            case TicketType.MOVIE: {
                MovieTicketViewHolder movieTicketViewHolder = (MovieTicketViewHolder) viewHolder;

                movieTicketViewHolder.movie.setText(item.getMovieName());
                movieTicketViewHolder.cinema.setText(item.getCinemaName());
                movieTicketViewHolder.rated.setText("C" + item.getMinAge());
                movieTicketViewHolder.runningTime.setText(item.getRunningTime());
                movieTicketViewHolder.movieType.setText(item.getMovieType());
                movieTicketViewHolder.date.setText(convertDate(item.getReleaseDate()));
                movieTicketViewHolder.time.setText(formatTime(item.getTime()));
                movieTicketViewHolder.room.setText(item.getRoom());

                Picasso.get().load(item.getImgURL()).error(R.drawable.poster).into(movieTicketViewHolder.portrait);

                movieTicketViewHolder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(context, PurchasedTicketDetailsActivity.class);
                    int pos = movieTicketViewHolder.getAdapterPosition();
                    intent.putExtra("purchased_ticket_info", bookingList.get(pos));
                    context.startActivity(intent);
                });

                break;
            }
            case TicketType.EVENT: {
                EventTicketViewHolder eventTicketViewHolder = (EventTicketViewHolder) viewHolder;

                eventTicketViewHolder.event.setText(item.getEventName());
                eventTicketViewHolder.venue.setText(item.getVenue());
                eventTicketViewHolder.releaseDate.setText(convertDate(item.getReleaseDate()));
                eventTicketViewHolder.time.setText(formatTime(item.getTime()));
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
