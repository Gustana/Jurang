package com.example.asus.jurang.ui.sell;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.jurang.Const;
import com.example.asus.jurang.R;
import com.example.asus.jurang.helper.api.RetrofitClient;
import com.example.asus.jurang.helper.model.AddBarangResponse;
import com.example.asus.jurang.helper.service.DataService;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanBarangActivity extends Activity implements ZXingScannerView.ResultHandler {

    private static final String TAG = ScanBarangActivity.class.getSimpleName();

    private ZXingScannerView scannerView;

    private int scan_barang_action_code;

    private String namaBarang, scan_result_barang;
    private int jumlahBarang, hargaBarang;

    private EditText edtNamaBarang, edtHargaBarang, edtJumlahBarang, edtKodeBarang;
    private Button btnKirimBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scan_barang_action_code = getIntent().getIntExtra(Const.scan_barang_key, 0);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
         scan_result_barang = result.getText();
         if (scan_barang_action_code == Const.add_barang_action_code){
             Dialog dialog = new Dialog(ScanBarangActivity.this);
             dialog.setContentView(R.layout.dialog_add_barang);

             initDialogAddBarangLayout(dialog);
             dialog.show();

             edtKodeBarang.setText(scan_result_barang);
             edtKodeBarang.setEnabled(false);

             onButtonKirimBarangClicked(dialog);
         }else{

         }
    }

    private void onButtonKirimBarangClicked(final Dialog dialog) {
        btnKirimBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInputDataBarang();

                DataService dataService = RetrofitClient.getInstance().create(DataService.class);
                Call<AddBarangResponse> call = dataService.sendDataBarang(namaBarang, scan_result_barang, hargaBarang, jumlahBarang);
                call.enqueue(new Callback<AddBarangResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<AddBarangResponse> call, @NonNull Response<AddBarangResponse> response) {
                        if (response.isSuccessful() && response.body() != null){
                            Toast.makeText(ScanBarangActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            onResume();
                        }else{
                            Toast.makeText(ScanBarangActivity.this, "OOps.. Something went wrong", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            Log.e(TAG, "onResponse: " + response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<AddBarangResponse> call, @NonNull Throwable t) {
                        Log.e(TAG, "onFailure: ", t.getCause());
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        call.cancel();
                    }
                });
            }
        });
    }

    private void getInputDataBarang() {
        namaBarang = edtNamaBarang.getText().toString();
        jumlahBarang = Integer.parseInt(edtJumlahBarang.getText().toString());
        hargaBarang = Integer.parseInt(edtHargaBarang.getText().toString());
    }

    private void initDialogAddBarangLayout(Dialog dialog) {
        edtNamaBarang = dialog.findViewById(R.id.edtNamaBarang);
        edtHargaBarang = dialog.findViewById(R.id.edtHargaBarang);
        edtJumlahBarang = dialog.findViewById(R.id.edtJumlahBarang);
        edtKodeBarang = dialog.findViewById(R.id.edtKodeBarang);
        btnKirimBarang = dialog.findViewById(R.id.btnKirimBarang);
    }

}
