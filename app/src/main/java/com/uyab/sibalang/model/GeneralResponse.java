package com.uyab.sibalang.model;

import com.google.gson.annotations.SerializedName;

public class GeneralResponse {
    @SerializedName("errCode")
    private String errCode;
    @SerializedName("message")
    private String message;

    public GeneralResponse(String errCode, String message) {
        this.errCode = errCode;
        this.message = message;
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
}
