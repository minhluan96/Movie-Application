package com.hcmus.movieapp.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.models.Calendar;

import java.util.List;

public class HorizontalCalendarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Calendar> calendars;
    private Context mContext;
    private Calendar selectedCalendar;
    private ItemCalendarListener listener;

    public HorizontalCalendarAdapter(List<Calendar> calendars, Context mContext) {
        this.calendars = calendars;
        this.mContext = mContext;
        this.selectedCalendar = calendars.get(0);
    }

    public void setListener(ItemCalendarListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.horizontal_item_calendar, viewGroup, false);
        return new HorizontalCalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        HorizontalCalendarViewHolder holder = (HorizontalCalendarViewHolder) viewHolder;
        Calendar calendar = calendars.get(i);
        holder.txtDateName.setText(calendar.getName());
        holder.txtDate.setText(calendar.getNumberOfDate());
        if (selectedCalendar == calendar) {
            holder.txtDateName.setTextColor(mContext.getResources().getColor(R.color.colorLightOrange));
            holder.imgSelectedDate.setVisibility(View.VISIBLE);
        } else {
            holder.txtDateName.setTextColor(mContext.getResources().getColor(android.R.color.white));
            holder.imgSelectedDate.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(v -> {
            int selectedPos = holder.getAdapterPosition();
            if (selectedPos < 0) return;
            selectedCalendar = calendars.get(selectedPos);
            listener.onCalendarSelected(selectedCalendar);
            notifyDataSetChanged();
        });
    }

    public interface ItemCalendarListener {
        void onCalendarSelected(Calendar calendar);
    }

    @Override
    public int getItemCount() {
        return calendars.size();
    }

    class HorizontalCalendarViewHolder extends RecyclerView.ViewHolder {

        private TextView txtDateName, txtDate;
        private ImageView imgSelectedDate;

        public HorizontalCalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtDateName = itemView.findViewById(R.id.txtDateName);
            imgSelectedDate = itemView.findViewById(R.id.imgBackgroundDate);
        }
    }

}
