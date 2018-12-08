package com.example.asus.jurang.helper.model;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

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
