package com.example.clock.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.clock.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {
            try {
               startActivity(new Intent(Splash.this,MainActivity.class));
               finish();

            } catch (Exception e) {
             }
        }, 1500);

    }
}