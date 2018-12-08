package com.example.asus.jurang.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.jurang.MainActivity;
import com.example.asus.jurang.R;
import com.example.asus.jurang.helper.db.DbHolder;
import com.example.asus.jurang.helper.db.model.ItemSellTableModel;
import com.example.asus.jurang.helper.model.server.GetBarangItem;

import java.util.List;

public class RecyclerSearchStockAdapter extends RecyclerView.Adapter<RecyclerSearchStockAdapter.RecyclerStockUI> {

    private static final String TAG = RecyclerSearchStockAdapter.class.getSimpleName();

    private Context context;
    private List<GetBarangItem> barangItems;

    private TextView txtItemStock, txtItemName;
    private EditText edtItemQuantity;
    private Button btnCancel, btnAddBarang;

    public RecyclerSearchStockAdapter(Context context, List<GetBarangItem> barangItems) {
        this.context = context;
        this.barangItems = barangItems;
    }

    @NonNull
    @Override
    public RecyclerStockUI onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_search_stok_item, viewGroup, false);

        return new RecyclerStockUI(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerStockUI recyclerStockUI, int i) {
        final GetBarangItem barangItem = barangItems.get(i);

        recyclerStockUI.txtItemStock.setText(barangItem.getJumlahBarang());
        recyclerStockUI.txtItemPrice.setText(barangItem.getHargaBarang());
        recyclerStockUI.txtItemName.setText(barangItem.getNamaBarang());
        recyclerStockUI.txtItemCode.setText(barangItem.getKodeBarang());

        recyclerStockUI.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_quantity_barang);

                initDialogQuantityLayout(dialog);

                txtItemName.setText(barangItem.getNamaBarang());
                txtItemStock.setText(barangItem.getJumlahBarang());

                btnAddBarang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int itemQuantity = Integer.parseInt(edtItemQuantity.getText().toString());
                        final int itemPrice = Integer.parseInt(barangItem.getHargaBarang());
                        final int itemStock = Integer.parseInt(barangItem.getJumlahBarang());

                        if (itemQuantity < Integer.parseInt(barangItem.getJumlahBarang())) {

                            final ItemSellTableModel itemSellTableModel = new ItemSellTableModel(barangItem.getNamaBarang(), barangItem.getKodeBarang(), itemPrice, itemStock, itemQuantity, (itemPrice * itemQuantity));

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    DbHolder.getDbHolderInstance(context).itemSellDaoService().insertItemSellData(itemSellTableModel);

                                    Intent i = new Intent(context, MainActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                    dialog.dismiss();

                                    context.startActivity(i);
                                }
                            }).start();
                        } else {
                            Toast.makeText(context, "Not enough stock", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    private void initDialogQuantityLayout(Dialog dialog) {
        txtItemName = dialog.findViewById(R.id.txtItemName);
        txtItemStock = dialog.findViewById(R.id.txtItemStock);
        edtItemQuantity = dialog.findViewById(R.id.edtItemQuantity);
        btnAddBarang = dialog.findViewById(R.id.btnAddBarang);
        btnCancel = dialog.findViewById(R.id.btnCancel);
    }

    @Override
    public int getItemCount() {
        return barangItems.size();
    }

    class RecyclerStockUI extends RecyclerView.ViewHolder {
        private TextView txtItemName, txtItemPrice, txtItemStock, txtItemCode;

        RecyclerStockUI(@NonNull View itemView) {
            super(itemView);
            txtItemCode = itemView.findViewById(R.id.txtItemCode);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtItemPrice = itemView.findViewById(R.id.txtItemPrice);
            txtItemStock = itemView.findViewById(R.id.txtItemStock);
        }
    }
}
