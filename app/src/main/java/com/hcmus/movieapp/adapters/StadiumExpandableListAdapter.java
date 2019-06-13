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
import com.hcmus.movieapp.models.ShowMatch;
import com.hcmus.movieapp.models.Sport;
import com.hcmus.movieapp.models.Venue;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class StadiumExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<Sport> sportList;
    private HashMap<Sport, List<ShowMatch>> dataChild;
    private Calendar calendar;
    private Venue venue;

    public StadiumExpandableListAdapter(Context mContext, List<Sport> sportList, HashMap<Sport, List<ShowMatch>> dataChild) {
        this.mContext = mContext;
        this.sportList = sportList;
        this.dataChild = dataChild;
    }

    public void setSportAndShowmatchs(List<Sport> sportList, HashMap<Sport, List<ShowMatch>> dataChild) {
        this.sportList = sportList;
        this.dataChild = dataChild;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return sportList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dataChild.get(sportList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return sportList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.dataChild.get(sportList.get(groupPosition)).get(childPosition);
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
        Sport sport = (Sport) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sport_expandable_list_group, null);
        }

        TextView txtEventName = convertView.findViewById(R.id.txtEventName);
        ImageView imgPoster = convertView.findViewById(R.id.imgPoster);
        TextView txtOrganizer = convertView.findViewById(R.id.txtOrganizer);
        TextView txtReleaseDate = convertView.findViewById(R.id.txtReleaseDate);
        TextView txtDescription = convertView.findViewById(R.id.txtDescription);

        txtEventName.setText(sport.getName());
        Picasso.get().load(sport.getImgUrl()).error(R.drawable.ic_launcher_background).into(imgPoster);
        txtOrganizer.setText(sport.getHost());
        txtReleaseDate.setText(sport.getReleaseDateStr());
        txtDescription.setText(sport.getDescription());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ShowMatch showMatch =  (ShowMatch) getChild(groupPosition, childPosition);
        Sport sport = sportList.get(groupPosition);

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

        if (showMatch != null) {
            timeStart = showMatch.getTimeStart();
            finishTime = showMatch.getLocation();
            totalSeat = showMatch.getNumberOfTicket() + " ghế";
            price = showMatch.getFormatedPrice();
        }

        txtTimeStart.setText(timeStart);
        txtTimeEnd.setText(finishTime);
        txtType.setText(totalSeat);
        txtPrice.setText("Từ " + price);

        ShowMatch finalShowMatch = showMatch;
        Venue finalVenue = venue;
        convertView.setOnClickListener(v -> {
            if (sport != null) {
                sendSportDataToIntent(sport, finalShowMatch, finalVenue);
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

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    private void sendSportDataToIntent(Sport sport, ShowMatch showMatch, Venue venue) {
        Intent intent = new Intent(mContext, BookingTicketActivity.class);
        Gson gson = new Gson();
        String jsonSport = gson.toJson(sport);
        String jsonShowMatch = gson.toJson(showMatch);
        String jsonStadium = gson.toJson(venue);
        String jsonCalendar = gson.toJson(calendar);
        intent.putExtra("sport", jsonSport);
        intent.putExtra("stadium", jsonStadium);
        intent.putExtra("showmatch", jsonShowMatch);
        intent.putExtra("calendar", jsonCalendar);
        mContext.startActivity(intent);
    }
}
