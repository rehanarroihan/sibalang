package com.uyab.sibalang.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Stuff implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("date")
    private String date;
    @SerializedName("photo")
    private String photo;
    @SerializedName("turned")
    private String turned;
    @SerializedName("nim")
    private String nim;

    public Stuff(String id, String name, String description, String date, String photo, String turned, String nim) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.photo = photo;
        this.turned = turned;
        this.nim = nim;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTurned() {
        return turned;
    }

    public void setTurned(String turned) {
        this.turned = turned;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.date);
        dest.writeString(this.photo);
        dest.writeString(this.turned);
        dest.writeString(this.nim);
    }

    protected Stuff(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.date = in.readString();
        this.photo = in.readString();
        this.turned = in.readString();
        this.nim = in.readString();
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
