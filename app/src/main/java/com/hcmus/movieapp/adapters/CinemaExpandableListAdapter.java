package com.hcmus.movieapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.activities.BookingTicketActivity;
import com.hcmus.movieapp.models.Calendar;
import com.hcmus.movieapp.models.Cinema;
import com.hcmus.movieapp.models.Movie;
import com.hcmus.movieapp.models.Showtime;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class CinemaExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<Movie> movieList;
    private HashMap<Movie, List<Showtime>> dataChild;
    private Calendar calendar;
    private Cinema cinema;

    public CinemaExpandableListAdapter(Context mContext, List<Movie> movieList, HashMap<Movie, List<Showtime>> dataChild) {
        this.mContext = mContext;
        this.movieList = movieList;
        this.dataChild = dataChild;
    }

    public void setMovieAndShowtimes(List<Movie> movieList, HashMap<Movie, List<Showtime>> dataChild) {
        this.movieList = movieList;
        this.dataChild = dataChild;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return movieList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dataChild.get(movieList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return movieList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.dataChild.get(movieList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Movie movie = (Movie) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.movie_expandable_list_group, null);
        }

        TextView txtMovieName = convertView.findViewById(R.id.txtMovieName);
        ImageView imgPoster = convertView.findViewById(R.id.imgPoster);
        TextView txtRated = convertView.findViewById(R.id.txtRated);
        TextView txtRunningTime = convertView.findViewById(R.id.txtRunningTime);
        TextView txtMovieType = convertView.findViewById(R.id.txtMovieType);

        txtMovieName.setText(movie.getName());
        Picasso.get().load(movie.getImgURL()).error(R.drawable.ic_launcher_background).into(imgPoster);
        txtRated.setText("C"+movie.getMinAge());
        txtRunningTime.setText(movie.getLength());
        txtMovieType.setText(movie.getType());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Showtime showtime = (Showtime) getChild(groupPosition, childPosition);
        Movie movie = movieList.get(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_child_item, null);
        }
        TextView txtTimeStart = convertView.findViewById(R.id.txtTimeStart);
        TextView txtTimeEnd = convertView.findViewById(R.id.txtTimeEnd);
        TextView txtType = convertView.findViewById(R.id.txtType);
        TextView txtPrice = convertView.findViewById(R.id.txtPrice);

        String timeStart = "";
        String finishTime = "";
        String totalSeat = "";
        String price = "";

        if (showtime != null) {
            timeStart = showtime.getTimeStart();
            finishTime = "~" + showtime.getFinishTime(movie.getLengthNumb());
            totalSeat = showtime.getTotalSeats() + " ghế";
            price = showtime.getFormatedPrice();
        }

        txtTimeStart.setText(timeStart);
        txtTimeEnd.setText(finishTime);
        txtType.setText(totalSeat);
        txtPrice.setText("Từ " + price);

        Showtime finalShowtime = showtime;
        Cinema finalCinema = cinema;
        convertView.setOnClickListener(v -> {
            if (movie != null) {
                sendMovieDataToIntent(movie, finalShowtime, finalCinema);
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    private void sendMovieDataToIntent(Movie movie, Showtime showtime, Cinema cinema) {
        Intent intent = new Intent(mContext, BookingTicketActivity.class);
        Gson gson = new Gson();
        String jsonMovie = gson.toJson(movie);
        String jsonShowtime = gson.toJson(showtime);
        String jsonCinema = gson.toJson(cinema);
        String jsonCalendar = gson.toJson(calendar);
        intent.putExtra("movie", jsonMovie);
        intent.putExtra("cinema", jsonCinema);
        intent.putExtra("showtime", jsonShowtime);
        intent.putExtra("calendar", jsonCalendar);
        mContext.startActivity(intent);
    }
}
