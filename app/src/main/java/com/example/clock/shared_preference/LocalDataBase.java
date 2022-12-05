package com.example.clock.shared_preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.clock.screens.MainActivity;

public class LocalDataBase {

    private static final String NAME = "SCHEDULER";
    private static final String CURRENT_TIME = "CURRENT_TIME";



    public static void saveScheduler(Float schedulerTime) {//for save store Id
        try {
            SharedPreferences preferences = MainActivity.context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putFloat("schedulerTime", schedulerTime);
            editor.apply();
        } catch (Exception e) {
        }
    }
   public static void scheduleTimeForSelected(Float schedulerTime) {//for save store Id
        try {
            SharedPreferences preferences = MainActivity.context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putFloat("schedulerTime", schedulerTime);
            editor.apply();
        } catch (Exception e) {
        }
    }


    public static Float getSchedulerForSelected() {
        SharedPreferences prfs = MainActivity.context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        Float schedulerTime = prfs.getFloat("schedulerTime", 0f);
        return schedulerTime;
    }

    public static Float getScheduler() {
        SharedPreferences prfs = MainActivity.context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        Float schedulerTime = prfs.getFloat("schedulerTime", 0f);
        return schedulerTime;
    }

    public static void deleteCustomerData( ) {
        SharedPreferences preferences = MainActivity.context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

        // saveScheduler(schedulerTime);
    }


    public static void setCurrentTime(Float currentTime) {//for save store Id
        try {
            SharedPreferences preferences =MainActivity. context.getSharedPreferences(CURRENT_TIME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putFloat("currentTime", currentTime);
            editor.apply();
        } catch (Exception e) {
        }


    }

    public static Float getCurrentTime() {//for save store Id
        SharedPreferences prfs =MainActivity. context.getSharedPreferences(CURRENT_TIME, Context.MODE_PRIVATE);
        Float schedulerTime = prfs.getFloat("currentTime", 0f);
        return schedulerTime;
    }

    public static void deleteCurrentTime() {
        SharedPreferences preferences = MainActivity.context.getSharedPreferences(CURRENT_TIME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

        // saveScheduler(schedulerTime);
    }




}
