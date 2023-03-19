package com.github.cptzee.cinemax;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomeFragment homeFragment = new HomeFragment();
        ProfileFragment profileFragment = new ProfileFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.home_bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}