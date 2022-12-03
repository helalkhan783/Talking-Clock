package com.example.clock.shared_preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.clock.screens.MainActivity;

public class LocalDataBase {
    Context context;
    private static final String NAME = "SCHEDULER";

    public LocalDataBase(Context context) {
        this.context = context;
    }

    public   void saveScheduler(Integer schedulerTime) {//for save store Id
        try {
            SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("schedulerTime", schedulerTime);
            editor.apply();
        }catch (Exception e){}
    }

    public   Integer getScheduler() {
        SharedPreferences prfs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        Integer schedulerTime = prfs.getInt("schedulerTime", 0);
        return schedulerTime;
    }

    public void deleteCustomerData(String schedulerTime) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

       // saveScheduler(schedulerTime);
    }

}
