package com.uyab.sibalang.model;

import com.google.gson.annotations.SerializedName;

public class Userdata {
    @SerializedName("id")
    private String id;
    @SerializedName("nim")
    private String nim;
    @SerializedName("full_name")
    private String full_name;
    @SerializedName("departmen")
    private String departmen;
    @SerializedName("program")
    private String program;
    @SerializedName("phone")
    private String phone;
    @SerializedName("position")
    private String position;

    public Userdata() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
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
