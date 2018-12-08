package com.example.asus.jurang;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.asus.jurang.ui.fragment.ProfileFragment;
import com.example.asus.jurang.ui.fragment.SaleFragment;
import com.example.asus.jurang.ui.fragment.StockFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);

        loadFragment(new SaleFragment());

        navigation.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_sale:
                return loadFragment(new SaleFragment());
            case R.id.navigation_stock:
                return loadFragment(new StockFragment());
            case R.id.navigation_profile:
                return loadFragment(new ProfileFragment());
        }
        return false;
    }

    private boolean loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContainer, fragment);
        transaction.commit();
        return true;
    }
}
