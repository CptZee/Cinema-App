package com.github.cptzee.cinemax;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.cptzee.cinemax.Data.Category;
import com.github.cptzee.cinemax.Data.CategoryHelper;
import com.github.cptzee.cinemax.Fragments.HomeFragment;
import com.github.cptzee.cinemax.Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Run this code once.
        SharedPreferences preferences = getSharedPreferences("First-Launch", MODE_PRIVATE);
        boolean firstStart = preferences.getBoolean("First-Launch", true);

        //TODO: Fix the first launch preference
        if(firstStart)
            setupDBDefaults(preferences);

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

    private void setupDBDefaults(SharedPreferences preferences){
        CategoryHelper categoryHelper = new CategoryHelper(this);
        categoryHelper.onCreate(categoryHelper.getWritableDatabase());
        Category data = new Category();

        String[] names = {"IMAX", "Ordinary", "3D", "Directors"};
        for(String n : names){
            data.setName(n);
            categoryHelper.insert(data);
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("First-Launch", false);
        editor.apply();
    }
}