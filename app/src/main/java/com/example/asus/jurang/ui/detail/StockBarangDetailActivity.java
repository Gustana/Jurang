package com.example.asus.jurang.ui.detail;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.jurang.Const;
import com.example.asus.jurang.R;
import com.example.asus.jurang.helper.api.RetrofitClient;
import com.example.asus.jurang.helper.model.server.AddBarangResponse;
import com.example.asus.jurang.helper.service.DataService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockBarangDetailActivity extends AppCompatActivity {

    private static final String TAG = StockBarangDetailActivity.class.getSimpleName();

    private TextView txtItemCode;
    private EditText edtItemName, edtItemStock, edtItemPrice;
    private ImageView imgEditItemName, imgEditItemStock, imgEditItemPrice, imgDeleteItem;
    private FloatingActionButton fabPickImage;
    private Button btnSaveDataItem;

    private String itemName, itemStock, itemPrice, itemCode;

    private DataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok_barang_detail);

        dataService = RetrofitClient.getInstance().create(DataService.class);

        initLayout();
        getBundledData();
        setBundledData();
        checkEditableMode();
        onButtonClicked();
    }

    private void setBundledData() {
        txtItemCode.setText(itemCode);
        edtItemName.setText(itemName);
        edtItemPrice.setText(itemPrice);
        edtItemStock.setText(itemStock);
    }

    private void getBundledData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            itemCode = bundle.getString(Const.detail_data_item_code_key);
            itemName = bundle.getString(Const.detail_data_item_name_key);
            itemStock = bundle.getString(Const.detail_data_item_stock_key);
            itemPrice = bundle.getString(Const.detail_data_item_price_key);
        } else {
            Log.e(TAG, "getBundledData: " + "Null data from adapter stock");
        }
    }

    private void checkEditableMode() {

        setEditableMode(imgEditItemName);
        setEditableMode(imgEditItemPrice);
        setEditableMode(imgEditItemStock);
    }

    private void setEditableMode(final ImageView imageView) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView == imgEditItemName) {
                    if (edtItemName.isEnabled()) {
                        edtItemName.setEnabled(false);
                    } else {
                        edtItemName.setEnabled(true);
                    }
                } else if (imageView == imgEditItemPrice) {
                    if (edtItemPrice.isEnabled()) {
                        edtItemPrice.setEnabled(false);
                    } else {
                        edtItemPrice.setEnabled(true);
                    }
                } else if (imageView == imgEditItemStock) {
                    if (edtItemStock.isEnabled()) {
                        edtItemStock.setEnabled(false);
                    } else {
                        edtItemStock.setEnabled(true);
                        edtItemStock.requestFocus();
                    }
                }
            }
        });


    }

    private void onButtonClicked() {
        btnSaveDataItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getNewItemDetailData();

                Call<AddBarangResponse> call = dataService.updateDataBarang(itemName, itemCode, Integer.parseInt(itemPrice), Integer.parseInt(itemStock));
                call.enqueue(new Callback<AddBarangResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<AddBarangResponse> call, @NonNull Response<AddBarangResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(StockBarangDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.i(TAG, "onResponse: " + response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<AddBarangResponse> call, @NonNull Throwable t) {
                        Log.e(TAG, "onFailure: ", t.getCause());
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        });

        fabPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(StockBarangDetailActivity.this);
                alertBuilder.setTitle("Delete Item " + itemName);
                alertBuilder.setMessage("Are you sure want to delete this item ?");
                alertBuilder.setIcon(R.drawable.ic_delete_tosca_24dp);
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call<AddBarangResponse> call = dataService.deleteDataBarang(itemCode);
                        call.enqueue(new Callback<AddBarangResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<AddBarangResponse> call, @NonNull Response<AddBarangResponse> response) {
                                if (response.isSuccessful() && response.body() != null){
                                    Toast.makeText(StockBarangDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }else{
                                    Log.i(TAG, "onResponse: " + response.body().toString());
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<AddBarangResponse> call, @NonNull Throwable t) {
                                call.cancel();
                                Log.e(TAG, "onFailure: ", t.getCause());
                                Log.e(TAG, "onFailure: " + t.getMessage() );
                            }
                        });
                    }
                });
                alertBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertBuilder.show();
            }
        });
    }

    private void getNewItemDetailData() {
        itemName = edtItemName.getText().toString();
        itemPrice = edtItemPrice.getText().toString();
        itemStock = edtItemStock.getText().toString();
    }

    private void initLayout() {
        txtItemCode = findViewById(R.id.txtItemCode);
        edtItemName = findViewById(R.id.edtItemName);
        edtItemStock = findViewById(R.id.edtItemStock);
        edtItemPrice = findViewById(R.id.edtItemPrice);
        imgEditItemName = findViewById(R.id.imgEditItemName);
        imgEditItemPrice = findViewById(R.id.imgEditItemPrice);
        imgEditItemStock = findViewById(R.id.imgEditItemStock);
        imgDeleteItem = findViewById(R.id.imgDeleteItem);
        fabPickImage = findViewById(R.id.fabPickImage);
        btnSaveDataItem = findViewById(R.id.btnSaveDataItem);

        edtItemStock.setEnabled(false);
        edtItemPrice.setEnabled(false);
        edtItemName.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
