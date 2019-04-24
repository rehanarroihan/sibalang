package com.uyab.sibalang.model;

import com.google.gson.annotations.SerializedName;

public class StuffDetail {
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
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("departmen")
    private String departmen;
    @SerializedName("program")
    private String program;
    @SerializedName("phone")
    private String phone;
    @SerializedName("position")
    private String position;

    public StuffDetail(String id, String name, String description, String date, String photo, String turned, String nim, String fullname, String departmen, String program, String phone, String position) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.photo = photo;
        this.turned = turned;
        this.nim = nim;
        this.fullname = fullname;
        this.departmen = departmen;
        this.program = program;
        this.phone = phone;
        this.position = position;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDepartmen() {
        return departmen;
    }

    public void setDepartmen(String departmen) {
        this.departmen = departmen;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
