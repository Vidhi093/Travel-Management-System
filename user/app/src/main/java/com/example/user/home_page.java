package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class home_page extends AppCompatActivity {

    //Button logout;
    BottomNavigationView bottomNavigationView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //logout = findViewById(R.id.logout);
        bottomNavigationView = findViewById(R.id.bottomnav);

        getSupportFragmentManager().beginTransaction().replace(R.id.constraintl, new Home_Fragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        fragment = new Home_Fragment();
                        break;

                    case R.id.nav_dest:
                        fragment = new Destination_Fragment();
                        break;

                    case R.id.nav_account:
                        fragment = new Account_Fragment();
                        break;

                    case R.id.nav_hotel:
                        fragment = new Search_Fragment();
                        break;
                }

                assert fragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.constraintl, fragment).commit();
                return true;
            }
        });

    }
}