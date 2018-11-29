package com.example.asus.jurang.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.asus.jurang.Const;

public class SPManager{

    private SharedPreferences.Editor spEditor;
    private SharedPreferences sharedPreferences;

    public SPManager(Context context){
        sharedPreferences = context.getSharedPreferences(Const.app_sp_key, Context.MODE_PRIVATE);
    }

    public void SPSaveString(@NonNull String key, @NonNull String value){
        spEditor = sharedPreferences.edit();
        spEditor.putString(key, value);
        spEditor.apply();
    }

    public void SPSaveInt(@NonNull String key, @NonNull int value){
        spEditor = sharedPreferences.edit();
        spEditor.putInt(key, value);
        spEditor.apply();
    }

    public String SPgetString(@NonNull String key){
        return sharedPreferences.getString(key, null);
    }

}
