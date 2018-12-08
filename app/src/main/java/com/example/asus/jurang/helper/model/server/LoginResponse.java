package com.example.asus.jurang.helper.model.server;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("error")
    private String errorCode;

    @SerializedName("message")
    private String message;

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}