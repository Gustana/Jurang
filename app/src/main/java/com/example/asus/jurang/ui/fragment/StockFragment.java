package com.example.asus.jurang.ui.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.jurang.Const;
import com.example.asus.jurang.R;
import com.example.asus.jurang.helper.api.RetrofitClient;
import com.example.asus.jurang.helper.model.server.AddBarangResponse;
import com.example.asus.jurang.helper.model.server.GetBarangItem;
import com.example.asus.jurang.helper.model.server.GetBarangResponse;
import com.example.asus.jurang.helper.service.DataService;
import com.example.asus.jurang.ui.adapter.RecyclerStokAdapter;
import com.example.asus.jurang.ui.sell.ScanBarangActivity;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockFragment extends Fragment {

    private static final String TAG = StockFragment.class.getSimpleName();

    private String namaBarang, kodeBarang;
    private int jumlahBarang, hargaBarang;

    private Context context;
    private RecyclerView rvStock;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerStokAdapter recyclerStokAdapter;
    private FloatingActionButton fabScan, fabManual;
    private EditText edtNamaBarang, edtKodeBarang, edtJumlahBarang, edtHargaBarang;
    private Button btnKirimBarang;

    private List<GetBarangItem> getBarangItemList;
    private List<String> dataNamaBarangList;


    private DataService dataService;

    public StockFragment() {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stock, container, false);

        dataService = RetrofitClient.getInstance().create(DataService.class);

        getBarangItemList = new ArrayList<>();
        dataNamaBarangList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());

        initLayout(view);
        getDataBarang();
        onButtonClicked();

        rvStock.setLayoutManager(linearLayoutManager);

        return view;
    }

    private void onButtonClicked() {
        fabScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ScanBarangActivity.class);
                i.putExtra(Const.scan_barang_key, Const.add_barang_manual_action_code);
                startActivity(i);
            }
        });

        fabManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_add_barang);
                initDialogAddBarangLayout(dialog);

                btnKirimBarang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getAddDataBarangValue();

                        Call<AddBarangResponse> call = dataService.sendDataBarang(namaBarang, kodeBarang, hargaBarang, jumlahBarang);
                        call.enqueue(new Callback<AddBarangResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<AddBarangResponse> call, @NonNull Response<AddBarangResponse> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Oops.. Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }

                            @Override
                            public void onFailure(@NonNull Call<AddBarangResponse> call, @NonNull Throwable t) {
                                call.cancel();
                                Log.e(TAG, "onFailure: ", t.getCause());
                                Log.e(TAG, "onFailure: " + t.getMessage());

                                dialog.dismiss();

                                Toast.makeText(context, "Oops.. Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                dialog.show();
            }
        });
    }

    private void getAddDataBarangValue() {
        kodeBarang = edtKodeBarang.getText().toString();
        namaBarang = edtNamaBarang.getText().toString();
        hargaBarang = Integer.parseInt(edtHargaBarang.getText().toString());
        jumlahBarang = Integer.parseInt(edtJumlahBarang.getText().toString());
    }

    private void initDialogAddBarangLayout(Dialog dialog) {
        edtHargaBarang = dialog.findViewById(R.id.edtHargaBarang);
        edtJumlahBarang = dialog.findViewById(R.id.edtJumlahBarang);
        edtKodeBarang = dialog.findViewById(R.id.edtKodeBarang);
        edtNamaBarang = dialog.findViewById(R.id.edtNamaBarang);
        btnKirimBarang = dialog.findViewById(R.id.btnKirimBarang);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataBarang();
    }

    private void getDataBarang() {
        Call<GetBarangResponse> call = dataService.getDataBarang();
        call.enqueue(new Callback<GetBarangResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetBarangResponse> call, @NonNull Response<GetBarangResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    getBarangItemList = response.body().getMessage();
                    recyclerStokAdapter = new RecyclerStokAdapter(getContext(), getBarangItemList);
                    rvStock.setAdapter(recyclerStokAdapter);
                    Log.i(TAG, "onResponse: " + dataNamaBarangList.toString());
                } else {
                    Toast.makeText(getContext(), "Opps.. Something went wrong", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: " + response.body());
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

    private void initLayout(View view) {
        rvStock = view.findViewById(R.id.rvStock);
        fabManual = view.findViewById(R.id.fabManual);
        fabScan = view.findViewById(R.id.fabScan);
    }

}
