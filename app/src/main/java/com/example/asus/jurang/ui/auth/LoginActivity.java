package com.example.asus.jurang.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.jurang.Const;
import com.example.asus.jurang.MainActivity;
import com.example.asus.jurang.R;
import com.example.asus.jurang.helper.api.RetrofitClient;
import com.example.asus.jurang.helper.model.server.LoginResponse;
import com.example.asus.jurang.helper.service.DataService;
import com.example.asus.jurang.manager.SPManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText edtEmail, edtPassword;
    private Button btnLogin;

    private String email, password;

    private SPManager spManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        spManager = new SPManager(this);

        initLayout();
        onButtonClicked();
    }

    private void onButtonClicked() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoginValue();

                DataService dataService = RetrofitClient.getInstance().create(DataService.class);
                Call<LoginResponse> call = dataService.login(email, password);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                        Log.i(TAG, "onResponse: error : " + response.body().getErrorCode());
                        Log.i(TAG, "onResponse: message : " + response.body().getMessage());

                        if (response.isSuccessful() && response.body() != null) {
                            if (Integer.valueOf(response.body().getErrorCode()) == 0) {
                                spManager.SPSaveString(Const.sp_email_key, response.body().getMessage());
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this, "Opps... Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Opps... Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                        Log.i(TAG, "onFailure: " + t.getMessage());
                        Log.e(TAG, "onFailure: ", t.getCause());
                    }
                });
            }
        });
    }

    private void getLoginValue() {
        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();
    }

    private void initLayout() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }
}
