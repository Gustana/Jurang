package com.example.asus.jurang.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.jurang.R;
import com.github.clans.fab.FloatingActionButton;

public class SaleFragment extends Fragment {

    private Context context;

    private ListView listSale;
    private TextView txtTotal;
    private Button btnBayar;
    private FloatingActionButton fabScan, fabManual;

    private String kodeBarang;


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

        initLayout(view);
        onButtonClicked();

        return view;
    }

    private void onButtonClicked() {
        fabScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        fabManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initLayout(View view) {
        listSale = view.findViewById(R.id.listSale);
        txtTotal = view.findViewById(R.id.txtTotal);
        btnBayar = view.findViewById(R.id.btnBayar);
        fabManual = view.findViewById(R.id.fabManual);
        fabScan = view.findViewById(R.id.fabScan);
    }
}
