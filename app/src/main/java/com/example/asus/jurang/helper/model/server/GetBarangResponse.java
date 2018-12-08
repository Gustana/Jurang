package com.example.asus.jurang.helper.model.server;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class GetBarangResponse {

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private List<GetBarangItem> message;

    public List<GetBarangItem> getMessage() {
        return message;
    }
}