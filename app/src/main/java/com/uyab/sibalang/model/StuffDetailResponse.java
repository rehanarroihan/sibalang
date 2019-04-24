package com.uyab.sibalang.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StuffDetailResponse {
    @SerializedName("errCode")
    private String errCode;
    @SerializedName("message")
    private String message;
    @SerializedName("stuffs")
    private Stuff stuff;

    public StuffDetailResponse(String errCode, String message, Stuff stuff) {
        this.errCode = errCode;
        this.message = message;
        this.stuff = stuff;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Stuff getStuff() {
        return stuff;
    }

    public void setStuff(Stuff stuff) {
        this.stuff = stuff;
    }
}
