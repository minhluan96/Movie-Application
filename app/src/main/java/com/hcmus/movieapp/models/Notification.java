package com.hcmus.movieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Notification implements Parcelable {
    private int id;
    private String imgURL;
    private String title;
    private String brief;
    private String content;
    private int type;
    private String createdAt;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(imgURL);
        dest.writeString(title);
        dest.writeString(brief);
        dest.writeString(content);
        dest.writeInt(type);
        dest.writeString(createdAt);
    }

    public static final Parcelable.Creator<Notification> CREATOR = new Parcelable.Creator<Notification>() {
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };

    public Notification(Parcel in) {
        this.id = in.readInt();
        this.imgURL = in.readString();
        this.title = in.readString();
        this.brief = in.readString();
        this.content = in.readString();
        this.type = in.readInt();
        this.createdAt = in.readString();
    }

    public Notification() {}

    public Notification(int id, String imgURL, String title, String brief, String content, int type, String createdAt) {
        this.id = id;
        this.imgURL = imgURL;
        this.title = title;
        this.brief = brief;
        this.content = content;
        this.type = type;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
