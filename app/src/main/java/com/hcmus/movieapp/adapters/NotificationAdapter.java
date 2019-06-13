package com.hcmus.movieapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.activities.NotificationDetailsActivity;
import com.hcmus.movieapp.models.Notification;
import com.hcmus.movieapp.utils.Utilities;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder>{
    private Context context;
    private List<Notification> notificationList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, brief, type, createdAt;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.txtTitle);
            createdAt = view.findViewById(R.id.txtCreatedAt);
            brief = view.findViewById(R.id.txtBrief);
            thumbnail = view.findViewById(R.id.imgThumbnail);
        }
    }

    public NotificationAdapter(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {
        final Notification item = notificationList.get(position);
        viewHolder.title.setText(item.getTitle());

        // Set icon to TextView
        Drawable dr = this.context.getResources().getDrawable(R.drawable.clock);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        // Scale it to 50 x 50
        Drawable drawable = new BitmapDrawable(this.context.getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));
        drawable.setColorFilter(this.context.getResources().getColor(R.color.textColorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        viewHolder.createdAt.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

        viewHolder.createdAt.setText(" " + Utilities.convertDateTime(item.getCreatedAt()));
        viewHolder.brief.setText(item.getBrief());

        Picasso.get().load(item.getImgURL()).error(R.drawable.poster).into(viewHolder.thumbnail);

        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, NotificationDetailsActivity.class);
            int pos = viewHolder.getAdapterPosition();
            intent.putExtra("notification_info", notificationList.get(pos));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public void setNotifications(List<Notification> notifications) {
        this.notificationList = notifications;
        this.notifyDataSetChanged();
    }
}
