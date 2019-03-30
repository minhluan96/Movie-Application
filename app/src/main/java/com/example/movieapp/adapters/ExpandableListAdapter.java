package com.example.movieapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.models.Cinema;
import com.example.movieapp.models.Showtime;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Cinema> cinemas;
    private HashMap<Cinema, List<Showtime>> dataChild;

    public ExpandableListAdapter(Context mContext, List<Cinema> cinemas, HashMap<Cinema, List<Showtime>> dataChild) {
        this.mContext = mContext;
        this.cinemas = cinemas;
        this.dataChild = dataChild;
    }

    @Override
    public int getGroupCount() {
        return cinemas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dataChild.get(cinemas.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return cinemas.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.dataChild.get(cinemas.get(groupPosition)).get(childPosition);
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
        Cinema cinema = (Cinema) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_list_group, null);
        }

        TextView txtCinemaName = convertView.findViewById(R.id.txtCinema);
        ImageView imgLogo = convertView.findViewById(R.id.imgLogo);

        txtCinemaName.setText(cinema.getName());
        Picasso.get().load(cinema.getImgUrl()).error(R.drawable.ic_launcher_background).into(imgLogo);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Showtime showtime = (Showtime) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_child_item, null);
        }
        TextView txtTimeStart = convertView.findViewById(R.id.txtTimeStart);
        TextView txtTimeEnd = convertView.findViewById(R.id.txtTimeEnd);
        TextView txtType = convertView.findViewById(R.id.txtType);
        TextView txtPrice = convertView.findViewById(R.id.txtPrice);
        TextView txtBranchName = convertView.findViewById(R.id.txtBranchName);
        TextView txtDistance = convertView.findViewById(R.id.txtDistance);

        txtTimeStart.setText(showtime.getTimeStart());
        txtTimeEnd.setText(showtime.getTimeEnd());
        txtType.setText(showtime.getTypeMovie());
        txtPrice.setText("~" + showtime.getPrice());
        txtBranchName.setText(showtime.getBranchName());
        txtDistance.setText(showtime.getDistance());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
