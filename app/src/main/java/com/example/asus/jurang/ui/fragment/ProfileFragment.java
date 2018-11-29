package com.example.asus.jurang.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.asus.jurang.Const;
import com.example.asus.jurang.R;
import com.example.asus.jurang.manager.SPManager;

public class ProfileFragment extends Fragment {

    private ImageView imgProfile, imgEditEmail;
    private FloatingActionButton fabPickImage;
    private EditText edtEmail;
    private Button btnSaveProfile;

    private String email;

    private SPManager spManager;

    private Context context;

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

}
