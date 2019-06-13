package com.hcmus.movieapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hcmus.movieapp.R;
import com.hcmus.movieapp.activities.CinemaInfoActivity;
import com.hcmus.movieapp.models.Cinema;
import com.hcmus.movieapp.models.Cineplex;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class CineplexExpandableAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Cineplex> cineplexList;
    private HashMap<Cineplex, List<Cinema>> dataChild;

    public CineplexExpandableAdapter(Context mContext, List<Cineplex> cineplexList, HashMap<Cineplex, List<Cinema>> dataChild) {
        this.mContext = mContext;
        this.cineplexList = cineplexList;
        this.dataChild = dataChild;
    }

    public void setCineplexAndCinemas(List<Cineplex> cineplexList, HashMap<Cineplex, List<Cinema>> dataChild) {
        this.cineplexList = cineplexList;
        this.dataChild = dataChild;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return cineplexList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dataChild.get(cineplexList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return cineplexList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.dataChild.get(cineplexList.get(groupPosition)).get(childPosition);
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
        Cineplex cineplex = (Cineplex) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_list_group, null);
        }

        TextView txtCineplexName = convertView.findViewById(R.id.txtName);
        ImageView imgLogo = convertView.findViewById(R.id.imgLogo);

        txtCineplexName.setText(cineplex.getName());
        Picasso.get().load(cineplex.getIconURL()).error(R.drawable.ic_launcher_background).into(imgLogo);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Cinema cinema = (Cinema) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cinema_expandable_child_item, null);
        }

        TextView txtCinemaName = convertView.findViewById(R.id.txtCinemaName);
        TextView txtAddress = convertView.findViewById(R.id.txtAddress);
        TextView txtAvgPoint = convertView.findViewById(R.id.txtAvgPoint);
        RatingBar ratingBar = convertView.findViewById(R.id.ratingBar);

        txtCinemaName.setText(cinema.getName());
        txtAddress.setText(cinema.getAddress());

        DecimalFormat df = new DecimalFormat("#.#");
        String formatted = df.format(cinema.getAvgPoint());
        txtAvgPoint.setText(formatted);

        ratingBar.setStepSize(0.01f);
        float ratingValue = cinema.getAvgPoint() * 0.5f;
        ratingBar.setRating(ratingValue);

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, CinemaInfoActivity.class);
            Gson gson = new Gson();
            String json = gson.toJson(cinema);
            intent.putExtra("title", cinema.getName());
            intent.putExtra("cinema", json);
            mContext.startActivity(intent);
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
