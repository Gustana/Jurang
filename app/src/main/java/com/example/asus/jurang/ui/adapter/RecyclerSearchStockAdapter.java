package com.example.asus.jurang.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.jurang.R;

public class RecyclerSearchStockAdapter extends RecyclerView.Adapter<RecyclerSearchStockAdapter.RecyclerStockUI> {

    @NonNull
    @Override
    public RecyclerStockUI onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_search_stok_item, viewGroup, false);
        return new RecyclerStockUI(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerStockUI recyclerStockUI, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class RecyclerStockUI extends RecyclerView.ViewHolder {

        RecyclerStockUI(@NonNull View itemView) {
            super(itemView);
        }
    }
}
