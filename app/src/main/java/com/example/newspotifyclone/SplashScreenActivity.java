package com.example.newspotifyclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.newspotifyclone.MainActivity;
import com.example.newspotifyclone.R;


public class SplashScreenActivity extends AppCompatActivity {
    private static long SPLASH_DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed( () -> {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
        },SPLASH_DURATION);
    }
}