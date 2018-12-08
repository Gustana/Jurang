package com.example.asus.jurang.helper.service;

import com.example.asus.jurang.helper.db.model.ItemSellTableModel;
import com.example.asus.jurang.helper.model.server.AddBarangResponse;
import com.example.asus.jurang.helper.model.server.GetBarangResponse;
import com.example.asus.jurang.helper.model.server.LoginResponse;
import com.example.asus.jurang.helper.model.server.RegisterResponse;
import com.example.asus.jurang.helper.model.server.SendImageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {

    @FormUrlEncoded
    @POST("auth/register.php")
    Call<RegisterResponse> register(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("auth/login.php")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("stock/addStock.php")
    Call<AddBarangResponse> sendDataBarang(
            @Field("namaBarang") String namaBarang,
            @Field("kodeBarang") String kodeBarang,
            @Field("hargaBarang") int hargaBarang,
            @Field("jumlahBarang") int jumlahBarang
    );

    @FormUrlEncoded
    @POST("stock/updateStock.php")
    Call<AddBarangResponse> updateDataBarang(
            @Field("namaBarang") String namaBarang,
            @Field("kodeBarang") String kodeBarang,
            @Field("hargaBarang") int hargaBarang,
            @Field("jumlahBarang") int jumlahBarang
    );

    @FormUrlEncoded
    @POST("stock/updateQuantityStock.php")
    Call<AddBarangResponse> updateQuantityBarang(
            @Field("itemCode") String itemCode,
            @Field("itemStockLeft") int itemStockLeft
    );

    @FormUrlEncoded
    @POST("stock/deleteStock.php")
    Call<AddBarangResponse> deleteDataBarang(
            @Field("kodeBarang") String kodeBarang
    );

    @FormUrlEncoded
    @POST("profile/uploadImage.php")
    Call<SendImageResponse> sendImage(
            @Field("email") String email,
            @Field("image") String image
    );

    @GET("stock/getStock.php")
    Call<GetBarangResponse> getDataBarang();
}
