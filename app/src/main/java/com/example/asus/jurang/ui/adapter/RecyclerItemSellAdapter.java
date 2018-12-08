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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.jurang.MainActivity;
import com.example.asus.jurang.R;
import com.example.asus.jurang.helper.db.DbHolder;
import com.example.asus.jurang.helper.db.model.ItemSellTableModel;

import java.util.List;

public class RecyclerItemSellAdapter extends RecyclerView.Adapter<RecyclerItemSellAdapter.RecyclerItemSellUI> {

    private EditText edtItemQuantityUpdate;
    private Button btnUpdateItemQuantity;
    private RadioGroup rgUpdateItemQuantity;

    private int newQuantity;

    private Context context;
    private List<ItemSellTableModel> itemSellModelList;

    public RecyclerItemSellAdapter(Context context, List<ItemSellTableModel> itemSellModelList) {
        this.context = context;
        this.itemSellModelList = itemSellModelList;
    }

    @NonNull
    @Override
    public RecyclerItemSellUI onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_sell_item, viewGroup, false);
        return new RecyclerItemSellUI(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerItemSellUI recyclerItemSellUI, int i) {
        final ItemSellTableModel sellItemModel = itemSellModelList.get(i);

        recyclerItemSellUI.txtItemQuantity.setText(String.valueOf(sellItemModel.getItemQuantity()));
        recyclerItemSellUI.txtItemPrice.setText(String.valueOf(sellItemModel.getItemPrice()));
        recyclerItemSellUI.txtItemName.setText(sellItemModel.getItemName());
        recyclerItemSellUI.txtItemTotalPrice.setText(String.valueOf(sellItemModel.getItemTotalPrice()));

        recyclerItemSellUI.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update_list_sell_barang);

                initLayoutDialogUpdateQuantityItem(dialog);

                btnUpdateItemQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int itemId = sellItemModel.getItemSell_Id();

                        int rbId = rgUpdateItemQuantity.getCheckedRadioButtonId();
                        if (rbId != -1) {
                            RadioButton radioButton = dialog.findViewById(rbId);

                            if (radioButton.getId() == R.id.rbDelete) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        DbHolder.getDbHolderInstance(context).itemSellDaoService().deleteItemById(itemId);
                                    }
                                }).start();

                                context.startActivity(new Intent(context, MainActivity.class));

                                dialog.dismiss();
                            } else if (radioButton.getId() == R.id.rbUpdate) {
                                getUpdateInputValue();

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        DbHolder.getDbHolderInstance(context).itemSellDaoService().updateItemQuantityById(newQuantity, itemId);
                                    }
                                }).start();

                                context.startActivity(new Intent(context, MainActivity.class));

                                dialog.dismiss();
                            }
                        }else{
                            Toast.makeText(context, "Please select your command", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });
    }

    private void getUpdateInputValue() {
        newQuantity = Integer.parseInt(edtItemQuantityUpdate.getText().toString());
    }

    private void initLayoutDialogUpdateQuantityItem(Dialog dialog) {
        rgUpdateItemQuantity = dialog.findViewById(R.id.rgUpdateItemQuantity);
        btnUpdateItemQuantity = dialog.findViewById(R.id.btnUpdateItemQuantity);
        edtItemQuantityUpdate = dialog.findViewById(R.id.edtItemQuantityUpdate);
    }

    @Override
    public int getItemCount() {
        return itemSellModelList.size();
    }

    class RecyclerItemSellUI extends RecyclerView.ViewHolder {

        private TextView txtItemName, txtItemPrice, txtItemQuantity, txtItemTotalPrice;

        RecyclerItemSellUI(@NonNull View itemView) {
            super(itemView);

            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtItemPrice = itemView.findViewById(R.id.txtItemPrice);
            txtItemQuantity = itemView.findViewById(R.id.txtItemQuantity);
            txtItemTotalPrice = itemView.findViewById(R.id.txtItemTotalPrice);
        }
    }
}
