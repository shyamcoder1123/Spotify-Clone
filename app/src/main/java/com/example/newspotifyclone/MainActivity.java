package com.example.newspotifyclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    Fragment selectedFragment;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainPart
                ,new HomeFragment()).commit();

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnItemSelectedListener(navListener);

    }
    private final NavigationBarView.OnItemSelectedListener navListener = item -> {
        selectedFragment = new HomeFragment();
        int itemId = item.getItemId();
        if(itemId == R.id.home){
            selectedFragment = new HomeFragment();
        }else if (itemId == R.id.search){
            selectedFragment = new SearchFragment();
        }else if (itemId == R.id.library){
            selectedFragment = new LibraryFragment();
        }else
            selectedFragment = new PremiumFragment();

        if(selectedFragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.mainPart
                    ,selectedFragment).commit();
        }
        return true;
    };
}