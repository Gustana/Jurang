package com.example.asus.jurang.helper.model.server;

import com.google.gson.annotations.SerializedName;

public class AddBarangResponse {

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private String message;

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}