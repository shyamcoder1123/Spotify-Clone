package com.example.newspotifyclone.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.newspotifyclone.R;


public class SplashScreenActivity extends AppCompatActivity {
    private static long SPLASH_DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.navigation_bar_color));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        Handler handler = new Handler();
        handler.postDelayed( () -> {
            startActivity(new Intent(SplashScreenActivity.this, LogInSignUpActivity.class));
            finish();
        },SPLASH_DURATION);
    }
}