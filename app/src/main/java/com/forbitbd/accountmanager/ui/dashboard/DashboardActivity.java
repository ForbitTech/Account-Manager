package com.forbitbd.accountmanager.ui.dashboard;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.forbitbd.accountmanager.R;
import com.forbitbd.accountmanager.ui.fragments.AccountsFragment;
import com.forbitbd.accountmanager.ui.fragments.HomeFragment;
import com.forbitbd.accountmanager.ui.fragments.TransactionsFragment;
import com.forbitbd.accountmanager.utils.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends BaseActivity {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        loadFragment(new HomeFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.dashboard) {
                    loadFragment(new HomeFragment());
                    return true;
                }else if (id == R.id.accounts){
                    loadFragment(new AccountsFragment());
                    return true;
                }else if (id == R.id.transactions){
                    loadFragment(new TransactionsFragment());
                    return true;
                }
                return false;
            }
        });

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container2, fragment);
        transaction.commit();
    }
}