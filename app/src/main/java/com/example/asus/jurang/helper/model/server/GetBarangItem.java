package com.example.asus.jurang.helper.model;

import com.google.gson.annotations.SerializedName;

public class GetBarangItem {

    @SerializedName("namaBarang")
    private String namaBarang;

    @SerializedName("kodeBarang")
    private String kodeBarang;

    @SerializedName("hargaBarang")
    private String hargaBarang;

    @SerializedName("jumlahBarang")
    private String jumlahBarang;

    public String getNamaBarang() {
        return namaBarang;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public String getHargaBarang() {
        return hargaBarang;
    }

    public String getJumlahBarang() {
        return jumlahBarang;
    }

    @Override
    public String toString() {
        return "GetBarangItem{" +
                "namaBarang='" + namaBarang + '\'' +
                ", kodeBarang='" + kodeBarang + '\'' +
                ", hargaBarang='" + hargaBarang + '\'' +
                ", jumlahBarang='" + jumlahBarang + '\'' +
                '}';
    }
}