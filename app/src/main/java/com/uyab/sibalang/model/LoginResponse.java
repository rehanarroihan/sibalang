package com.uyab.sibalang.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("errCode")
    private String errCode;
    @SerializedName("message")
    private String message;
    @SerializedName("userdata")
    private Userdata userdata;

    public LoginResponse() {}

    public LoginResponse(String errCode, String message, Userdata userdata) {
        this.errCode = errCode;
        this.message = message;
        this.userdata = userdata;
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

    public Userdata getUserdata() {
        return userdata;
    }

    public void setUserdata(Userdata userdata) {
        this.userdata = userdata;
    }
}
