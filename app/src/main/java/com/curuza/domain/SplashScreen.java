package com.curuza.domain;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.curuza.R;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        new Handler().postDelayed(() -> {
            Intent homeIntent= new Intent(SplashScreen.this,MainActivity.class);
            startActivity(homeIntent);
            finish();
        },SPLASH_TIME_OUT);

    }
}
