package com.hcmus.movieapp.fragments;

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.models.Notification;
import com.hcmus.movieapp.utils.Utilities;
import com.squareup.picasso.Picasso;

public class NotificationInfoFragment extends BaseFragment {

    private TextView txtTitle, txtCreatedAt, txtBrief, txtContent;
    private ImageView imgThumbnail;

    private Notification notificationInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.notification_info_fragment, container, false);

        notificationInfo = getArguments().getParcelable("notification_info");

        txtTitle = v.findViewById(R.id.txtTitle);
        txtCreatedAt = v.findViewById(R.id.txtCreatedAt);
        txtBrief = v.findViewById(R.id.txtBrief);
        txtContent = v.findViewById(R.id.txtContent);
        imgThumbnail = v.findViewById(R.id.imgThumbnail);

        txtTitle.setText(notificationInfo.getTitle());

        // Set icon to TextView
        Drawable dr = this.getResources().getDrawable(R.drawable.clock);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        // Scale it to 50 x 50
        Drawable drawable = new BitmapDrawable(this.getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));
        drawable.setColorFilter(this.getResources().getColor(R.color.textColorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
        txtCreatedAt.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        txtCreatedAt.setText(" " + Utilities.convertDateTime(notificationInfo.getCreatedAt()));

        String brief = notificationInfo.getBrief();
        txtBrief.setText(Html.fromHtml(brief.replace("\n", "<br><br>")));

        String content = notificationInfo.getContent();
        txtContent.setText(Html.fromHtml(content.replace("\n", "<br><br>")));

        Picasso.get().load(notificationInfo.getImgURL()).error(R.drawable.poster).into(imgThumbnail);

        return v;
    }
}
