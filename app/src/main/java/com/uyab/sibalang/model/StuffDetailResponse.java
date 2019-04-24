package com.uyab.sibalang.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StuffDetailResponse {
    @SerializedName("errCode")
    private String errCode;
    @SerializedName("message")
    private String message;
    @SerializedName("stuff")
    private StuffDetail stuff;

    public StuffDetailResponse() {}

    public StuffDetailResponse(String errCode, String message, StuffDetail stuff) {
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

    public StuffDetail getStuff() {
        return stuff;
    }

    public void setStuff(StuffDetail stuff) {
        this.stuff = stuff;
    }
}
