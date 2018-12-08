package com.example.asus.jurang.ui.sell;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.asus.jurang.R;
import com.example.asus.jurang.helper.api.RetrofitClient;
import com.example.asus.jurang.helper.model.server.GetBarangItem;
import com.example.asus.jurang.helper.model.server.GetBarangResponse;
import com.example.asus.jurang.helper.service.DataService;
import com.example.asus.jurang.ui.adapter.RecyclerSearchStockAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchManualBarangActivity extends AppCompatActivity {

    private static final String TAG = SearchManualBarangActivity.class.getSimpleName();

    private RecyclerView rvListBarang;
    private List<GetBarangItem> getBarangItemList;
    private RecyclerSearchStockAdapter recyclerSearchStockAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_manual_barang);

        getBarangItemList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);

        initLayout();
        getDataBarang();

        rvListBarang.setLayoutManager(linearLayoutManager);
    }

    private void getDataBarang() {
        DataService dataService = RetrofitClient.getInstance().create(DataService.class);
        Call<GetBarangResponse> call = dataService.getDataBarang();
        call.enqueue(new Callback<GetBarangResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetBarangResponse> call, @NonNull Response<GetBarangResponse> response) {
                Log.i(TAG, "onResponse: " + response.body());
                if (response.isSuccessful() && response.body() != null) {
                    getBarangItemList = response.body().getMessage();
                    recyclerSearchStockAdapter = new RecyclerSearchStockAdapter(SearchManualBarangActivity.this, getBarangItemList);
                    rvListBarang.setAdapter(recyclerSearchStockAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetBarangResponse> call, @NonNull Throwable t) {
                call.cancel();
                Log.e(TAG, "onFailure: ", t.getCause());
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void initLayout() {
        rvListBarang = findViewById(R.id.rvListBarang);
    }
}
