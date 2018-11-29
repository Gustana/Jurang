package com.example.asus.jurang.ui.auth;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.jurang.R;
import com.example.asus.jurang.helper.api.RetrofitClient;
import com.example.asus.jurang.helper.model.RegisterResponse;
import com.example.asus.jurang.helper.service.DataService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private EditText edtEmail, edtPassword;
    private Button btnRegister;

    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initLayout();
        onButtonClicked();
    }

    private void onButtonClicked() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRegisterValue();

                DataService dataService = RetrofitClient.getInstance().create(DataService.class);
                Call<RegisterResponse> call = dataService.register(email,password);
                call.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {
                        Log.i(TAG, "onResponse: " + response.message());
                        Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<RegisterResponse> call, @NonNull Throwable t) {
                        call.cancel();
                        Log.e(TAG, "onFailure: ", t.getCause());
                    }
                });
            }
        });
    }

    private void getRegisterValue() {
        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();
    }

    private void initLayout() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);
    }
}
