package com.uyab.sibalang.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Stuff implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("type")
    private String type;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("date")
    private String date;
    @SerializedName("photo")
    private String photo;
    @SerializedName("claimer")
    private String claimer;

    public Stuff() {}

    public Stuff(String id, String id_user, String type, String name, String description, String date, String photo, String claimer) {
        this.id = id;
        this.id_user = id_user;
        this.type = type;
        this.name = name;
        this.description = description;
        this.date = date;
        this.photo = photo;
        this.claimer = claimer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getClaimer() {
        return claimer;
    }

    public void setClaimer(String claimer) {
        this.claimer = claimer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.id_user);
        dest.writeString(this.type);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.date);
        dest.writeString(this.photo);
        dest.writeString(this.claimer);
    }

    protected Stuff(Parcel in) {
        this.id = in.readString();
        this.id_user = in.readString();
        this.type = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.date = in.readString();
        this.photo = in.readString();
        this.claimer = in.readString();
    }

    public static final Parcelable.Creator<Stuff> CREATOR = new Parcelable.Creator<Stuff>() {
        @Override
        public Stuff createFromParcel(Parcel source) {
            return new Stuff(source);
        }

        @Override
        public Stuff[] newArray(int size) {
            return new Stuff[size];
        }
    };
}
