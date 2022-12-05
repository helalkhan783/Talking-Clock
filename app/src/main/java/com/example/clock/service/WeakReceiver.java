package com.example.clock.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.clock.screens.MainActivity;
import com.example.clock.screens.WakeUpActivity;

public class WeakReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, WakeUpActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
       // MainActivity.setDataToScheduler("");


    }
}
