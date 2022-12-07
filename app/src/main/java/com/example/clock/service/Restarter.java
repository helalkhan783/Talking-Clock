package com.example.clock.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.example.clock.shared_preference.LocalDataBase;

public class Restarter extends BroadcastReceiver {
    LocalDataBase localDatabase;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Broadcast Listened", "Service tried to stop");
        Toast.makeText(context, "Service restarted", Toast.LENGTH_SHORT).show();
        localDatabase = new LocalDataBase(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, YourService.class));
        } else {
            context.startService(new Intent(context, YourService.class));
        }
    }
}
