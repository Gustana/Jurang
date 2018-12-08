package com.example.asus.jurang.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.jurang.Const;
import com.example.asus.jurang.R;
import com.example.asus.jurang.helper.model.server.GetBarangItem;
import com.example.asus.jurang.ui.detail.StockBarangDetailActivity;

import java.util.List;

public class RecyclerStokAdapter extends RecyclerView.Adapter<RecyclerStokAdapter.RecyclerStokAdapterUI> {

    private List<GetBarangItem> barangItems;
    private Context context;

    public RecyclerStokAdapter(Context context, List<GetBarangItem> barangItems) {
        this.context = context;
        this.barangItems = barangItems;
    }

    @NonNull
    @Override
    public RecyclerStokAdapter.RecyclerStokAdapterUI onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_stock_item, viewGroup, false);
        return new RecyclerStokAdapterUI(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerStokAdapter.RecyclerStokAdapterUI recyclerStokAdapterUI, int i) {
        final GetBarangItem barangItem = barangItems.get(i);
        recyclerStokAdapterUI.txtItemName.setText(barangItem.getNamaBarang());
        recyclerStokAdapterUI.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Const.detail_data_item_name_key, barangItem.getNamaBarang());
                bundle.putString(Const.detail_data_item_code_key, barangItem.getKodeBarang());
                bundle.putString(Const.detail_data_item_price_key, barangItem.getHargaBarang());
                bundle.putString(Const.detail_data_item_stock_key, barangItem.getJumlahBarang());
                Intent i = new Intent(context, StockBarangDetailActivity.class);
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return barangItems.size();
    }

    class RecyclerStokAdapterUI extends RecyclerView.ViewHolder {
        private TextView txtItemName;

        RecyclerStokAdapterUI(@NonNull View itemView) {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.txtItemName);
        }
    }
}
