package com.example.asus.jurang.ui.fragment;


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
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.jurang.Const;
import com.example.asus.jurang.R;
import com.example.asus.jurang.helper.api.RetrofitClient;
import com.example.asus.jurang.helper.db.DbHolder;
import com.example.asus.jurang.helper.db.model.ItemSellTableModel;
import com.example.asus.jurang.helper.model.server.AddBarangResponse;
import com.example.asus.jurang.helper.service.DataService;
import com.example.asus.jurang.ui.adapter.RecyclerItemSellAdapter;
import com.example.asus.jurang.ui.sell.ScanBarangActivity;
import com.example.asus.jurang.ui.sell.SearchManualBarangActivity;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleFragment extends Fragment {

    private static final String TAG = SaleFragment.class.getSimpleName();

    private Context context;

    private RecyclerView rvListSale;
    private TextView txtTotal;
    private Button btnBayar;
    private FloatingActionButton fabScan, fabManual;

    private List<ItemSellTableModel> itemSellModelList;
    private RecyclerItemSellAdapter recyclerItemSellAdapter;

    public SaleFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sale, container, false);

        itemSellModelList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);

        fetchSellItemData();
        initLayout(view);
        onButtonClicked();

        rvListSale.setLayoutManager(linearLayoutManager);

        return view;
    }

    private void fetchSellItemData() {
        FetchDataThread fetchDataThread = new FetchDataThread();
        fetchDataThread.start();

        Log.i(TAG, "fetchSellItemData1: " + itemSellModelList.toString());
    }

    private void onButtonClicked() {
        fabScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ScanBarangActivity.class);
                i.putExtra(Const.scan_barang_key, Const.add_barang_scan_action_code);
                startActivity(i);
            }
        });

        fabManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SearchManualBarangActivity.class));
            }
        });

        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick : ItemSellModelList : " + itemSellModelList.toString());

                for (ItemSellTableModel itemSellTableModel : itemSellModelList) {
                    int itemStock = itemSellTableModel.getItemStock();
                    int itemQuantity = itemSellTableModel.getItemQuantity();
                    int itemStockLeft = itemStock - itemQuantity;

                    DataService service = RetrofitClient.getInstance().create(DataService.class);
                    Call<AddBarangResponse> call = service.updateQuantityBarang(itemSellTableModel.getItemCode(), itemStockLeft);
                    call.enqueue(new Callback<AddBarangResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<AddBarangResponse> call, @NonNull Response<AddBarangResponse> response) {
                            Log.i(TAG, "onResponse: " + response.body().getMessage());
                            if (response.isSuccessful() && response.body() != null) {
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                DeleteDataThread deleteDataThread = new DeleteDataThread();
                                deleteDataThread.start();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<AddBarangResponse> call, @NonNull Throwable t) {
                            call.cancel();
                            Log.e(TAG, "onFailure: UpdateQuantityStock :", t.getCause());
                            Log.e(TAG, "onFailure: UpdateQuantityStock :" + t.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void initLayout(View view) {
        rvListSale = view.findViewById(R.id.rvListSale);
        txtTotal = view.findViewById(R.id.txtTotal);
        btnBayar = view.findViewById(R.id.btnBayar);
        fabManual = view.findViewById(R.id.fabManual);
        fabScan = view.findViewById(R.id.fabScan);
    }

    private class FetchDataThread extends Thread {
        @Override
        public void run() {
            super.run();

            itemSellModelList.addAll(DbHolder.getDbHolderInstance(context).itemSellDaoService().getAllItemSellData());
            recyclerItemSellAdapter = new RecyclerItemSellAdapter(context, itemSellModelList);
            try {
                rvListSale.setAdapter(recyclerItemSellAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.i(TAG, "fetchSellItemData: " + itemSellModelList.toString());

            int totalPrice = 0;
            for (ItemSellTableModel data : itemSellModelList) {
                totalPrice += data.getItemTotalPrice();
            }
            txtTotal.setText(String.valueOf(totalPrice));
            Log.i(TAG, "run: TotalPrice : " + totalPrice);
        }
    }

    private class DeleteDataThread extends Thread{
        @Override
        public void run() {
            super.run();

            DbHolder.getDbHolderInstance(context).itemSellDaoService().deleteAllData(itemSellModelList);
        }
    }

}

