package com.example.clock.screens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.KeyguardManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.clock.R;
import com.example.clock.service.CurrentTime;

import java.util.Locale;

public class WakeUpActivity extends AppCompatActivity {

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up);
     try {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
             setShowWhenLocked(true);
             setTurnScreenOn(true);
             KeyguardManager keyguardManager = (KeyguardManager) getSystemService(this.KEYGUARD_SERVICE);
             keyguardManager.requestDismissKeyguard(this, null);
         } else {
             getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                     WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                     WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                     WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
         }
     }catch (Exception e){
         Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
     }
    }
}