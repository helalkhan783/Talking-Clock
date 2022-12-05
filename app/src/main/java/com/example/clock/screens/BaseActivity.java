package com.example.clock.screens;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clock.service.Restarter;

public abstract class BaseActivity extends AppCompatActivity {
    public void reStartService(){
       Intent broadcastIntent = new Intent();
       broadcastIntent.setAction("restartservice");
       broadcastIntent.setClass(this, Restarter.class);
       this.sendBroadcast(broadcastIntent);
   }
}
