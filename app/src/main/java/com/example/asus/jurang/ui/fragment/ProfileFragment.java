package com.example.asus.jurang.ui.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.jurang.Const;
import com.example.asus.jurang.R;
import com.example.asus.jurang.helper.api.RetrofitClient;
import com.example.asus.jurang.helper.model.server.SendImageResponse;
import com.example.asus.jurang.helper.service.DataService;
import com.example.asus.jurang.manager.SPManager;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();

    private ImageView imgProfile, imgEditEmail;
    private FloatingActionButton fabPickImage;
    private EditText edtEmail;
    private Button btnSaveProfile;

    private String email, convertImage;

    private SPManager spManager;

    private Context context;
    private Intent intent;

    private Uri uri;
    private Bitmap bitmap;
    private ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArray;

    public ProfileFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        byteArrayOutputStream = new ByteArrayOutputStream();

        getSPValue();
        initLayout(view);
        setSpValue();

        onButtonClicked();
        return view;
    }

    private void setSpValue() {
        edtEmail.setText(email);
    }

    private void getSPValue() {
        spManager = new SPManager(context);

        email = spManager.SPgetString(Const.sp_email_key);
    }

    private void onButtonClicked() {
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInputData();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
                byteArray = byteArrayOutputStream.toByteArray();
                convertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                DataService dataService = RetrofitClient.getInstance().create(DataService.class);
                Call<SendImageResponse> call = dataService.sendImage(email, convertImage);
                call.enqueue(new Callback<SendImageResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<SendImageResponse> call, @NonNull Response<SendImageResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SendImageResponse> call, @NonNull Throwable t) {
                        call.cancel();
                        Log.e(TAG, "onFailure: ", t.getCause());
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        });

        fabPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setTitle("Pick Image");
                alertBuilder.setMessage("Choose a way you want to pick an image");

                alertBuilder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertBuilder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, Const.camera_permission_code);
                    }
                });

                alertBuilder.show();
            }
        });
    }

    private void getInputData() {
        email = edtEmail.getText().toString();
    }

    private void initLayout(View view) {
        imgProfile = view.findViewById(R.id.imgProfile);
        imgEditEmail = view.findViewById(R.id.imgEditEmail);
        fabPickImage = view.findViewById(R.id.fabPickImage);
        edtEmail = view.findViewById(R.id.edtEmail);
        btnSaveProfile = view.findViewById(R.id.btnSaveProfile);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != 0 && resultCode == RESULT_OK && data != null && data.getExtras() != null) {
            if (requestCode == Const.camera_permission_code) {
                bitmap = (Bitmap) data.getExtras().get("data");
                imgProfile.setImageBitmap(bitmap);
            }
        }
    }
}
