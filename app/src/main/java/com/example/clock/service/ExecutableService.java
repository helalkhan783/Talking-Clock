package com.example.clock.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.clock.screens.MainActivity;
import com.example.clock.shared_preference.LocalDataBase;

public class ExecutableService extends BroadcastReceiver {


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        MainActivity.setDataToScheduler("sdf");
    //  new MainActivity().setTime(LocalDataBase.getScheduler(), context);
    }
}
