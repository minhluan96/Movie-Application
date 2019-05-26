package com.hcmus.movieapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.activities.BookingTicketActivity;
import com.hcmus.movieapp.models.Calendar;
import com.hcmus.movieapp.models.Cinema;
import com.hcmus.movieapp.models.Movie;
import com.hcmus.movieapp.models.ShowMatch;
import com.hcmus.movieapp.models.Showtime;
import com.hcmus.movieapp.models.Sport;
import com.hcmus.movieapp.models.Stadium;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Cinema> cinemas;
    private List<Stadium> stadiums;
    private HashMap<Cinema, List<Showtime>> dataChild;
    private HashMap<Stadium, List<ShowMatch>> stadiumListHashMap;
    private Movie movie;
    private Sport sport;
    private Calendar calendar;
    private int selectedCinema = -1;

    public ExpandableListAdapter(Context mContext, List<Cinema> cinemas, HashMap<Cinema, List<Showtime>> dataChild) {
        this.mContext = mContext;
        this.cinemas = cinemas;
        this.dataChild = dataChild;
    }

    public ExpandableListAdapter(Context mContext, List<Stadium> stadiums, HashMap<Stadium, List<ShowMatch>> stadiumListHashMap, int type) {
        this.mContext = mContext;
        this.stadiums = stadiums;
        this.stadiumListHashMap = stadiumListHashMap;
    }

    public void setCinemaAndShowtimes(List<Cinema> cinemas, HashMap<Cinema, List<Showtime>> dataChild) {
        this.cinemas = cinemas;
        this.dataChild = dataChild;
        notifyDataSetChanged();
    }

    public void setStadumAndShowTimes(List<Stadium> stadiums, HashMap<Stadium, List<ShowMatch>> data) {
        this.stadiums = stadiums;
        this.stadiumListHashMap = data;
        notifyDataSetChanged();
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    @Override
    public int getGroupCount() {
        if (movie != null) {
            return cinemas.size();
        }
        return stadiums.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (movie != null) {
            return dataChild.get(cinemas.get(groupPosition)).size();
        }
        return stadiumListHashMap.get(stadiums.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (movie != null) {
            return cinemas.get(groupPosition);
        }
        return stadiums.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (movie != null) {
            return this.dataChild.get(cinemas.get(groupPosition)).get(childPosition);
        }
        return this.stadiumListHashMap.get(stadiums.get(groupPosition)).get(childPosition);
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
        Cinema cinema = null;
        Stadium stadium = null;
        if (movie != null) {
            cinema = (Cinema) getGroup(groupPosition);
        } else {
            stadium = (Stadium) getGroup(groupPosition);
        }
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_list_group, null);
        }

        TextView txtCinemaName = convertView.findViewById(R.id.txtName);
        ImageView imgLogo = convertView.findViewById(R.id.imgLogo);
        String name = "";
        String imgUrl = "";
        if (movie != null) {
            name = cinema.getName();
            imgUrl = cinema.getImgUrl();
        } else {
            name = stadium.getName();
            imgUrl = stadium.getIconURL();
        }

        txtCinemaName.setText(name);
        Picasso.get().load(imgUrl).error(R.drawable.ic_launcher_background).into(imgLogo);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Showtime showtime = null;
        ShowMatch showMatch = null;
        Stadium stadium = null;
        Cinema cinema = null;
        if (movie != null) {
            showtime = (Showtime) getChild(groupPosition, childPosition);
            cinema = cinemas.get(groupPosition);
        } else {
            showMatch = (ShowMatch) getChild(groupPosition, childPosition);
            stadium = stadiums.get(groupPosition);
        }

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

        if (movie != null) {
            timeStart = showtime.getTimeStart();
            finishTime = "~" + showtime.getFinishTime(movie.getLengthNumb());
            totalSeat = showtime.getTotalSeats() + " ghế";
            price = showtime.getFormatedPrice();
        } else {
            timeStart = showMatch.getTimeStart();
            finishTime = showMatch.getLocation();
            totalSeat = showMatch.getNumberOfTicket() + " ghế";
            price = showMatch.getFormatedPrice();
        }


        txtTimeStart.setText(timeStart);
        txtTimeEnd.setText(finishTime);
        txtType.setText(totalSeat);
        txtPrice.setText("Từ " + price);

        Showtime finalShowtime = showtime;
        Cinema finalCinema = cinema;
        ShowMatch finalShowMatch = showMatch;
        Stadium finalStadium = stadium;
        convertView.setOnClickListener(v -> {
            if (movie != null) {
                sendMovieDataToIntent(movie, finalShowtime, finalCinema);
            } else {
                sendSportDataToIntent(sport, finalShowMatch, finalStadium);
            }
        });

        return convertView;
    }

    private void sendSportDataToIntent(Sport sport, ShowMatch showMatch, Stadium stadium) {
        Intent intent = new Intent(mContext, BookingTicketActivity.class);
        Gson gson = new Gson();
        String jsonMovie = gson.toJson(sport);
        String jsonShowMatch = gson.toJson(showMatch);
        String jsonStadium = gson.toJson(stadium);
        String jsonCalendar = gson.toJson(calendar);
        intent.putExtra("sport", jsonMovie);
        intent.putExtra("stadium", jsonStadium);
        intent.putExtra("showmatch", jsonShowMatch);
        intent.putExtra("calendar", jsonCalendar);
        mContext.startActivity(intent);
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


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
