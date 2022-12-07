package com.example.clock.shared_preference;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalDataBase {
    Context context;

    public LocalDataBase(Context context) {
        this.context = context;
    }

    private static final String NAME = "SCHEDULER";
    private static final String SELECTED_SCHEDULER = "SELECTED_SCHEDULER";
    private static final String SET_CURRENT_TIME = "CURRENT_TIME";
    private static final String SPEECT_STATUS = "SPEECT_STATUS";


    public void saveScheduler(String schedulerTime) {//for save store Id

        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("schedulerTime", schedulerTime);
        editor.apply();

    }

    public void scheduleTimeForSelected(String selectedScheduleTime) {//for save store Id
        try {
            SharedPreferences preferences = context.getSharedPreferences(SELECTED_SCHEDULER, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("selectedScheduleTime", selectedScheduleTime);
            editor.apply();
        } catch (Exception e) {
        }
    }

    public void deletescheduleTimeForSelecteda() {
        SharedPreferences preferences = context.getSharedPreferences(SELECTED_SCHEDULER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

        // saveScheduler(schedulerTime);
    }

    public Integer getSchedulerForSelected() {
        SharedPreferences prfs = context.getSharedPreferences(SELECTED_SCHEDULER, Context.MODE_PRIVATE);
        String schedulerTime = prfs.getString("selectedScheduleTime", "0");
        return Integer.parseInt(schedulerTime);
    }

    public Integer getScheduler() {
        SharedPreferences prfs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        String schedulerTime = prfs.getString("schedulerTime", "0");
        return Integer.parseInt(schedulerTime);
    }

    public void deleteScheduler() {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

        // saveScheduler(schedulerTime);
    }


    public void setCurrentTime(String currentTime) {//for save  Id
        try {
            SharedPreferences preferences = context.getSharedPreferences(SET_CURRENT_TIME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("currentTime1", currentTime);
            editor.apply();
        } catch (Exception e) {
        }


    }

    public String getCurrentTime() {//for save store Id
        SharedPreferences prfs = context.getSharedPreferences(SET_CURRENT_TIME, Context.MODE_PRIVATE);
        String schedulerTime = prfs.getString("currentTime1", "0");
        return schedulerTime;
    }

    public void deleteCurrentTime() {
        SharedPreferences preferences = context.getSharedPreferences(SET_CURRENT_TIME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

        // saveScheduler(schedulerTime);
    }

    public void timeSpeechStatusSet(boolean status) {//for save store Id
        try {
            SharedPreferences preferences = context.getSharedPreferences(SPEECT_STATUS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("status", status);
            editor.apply();
        } catch (Exception e) {
        }
    }

    public Boolean getSpeechStatus() {//for save store Id
        SharedPreferences prfs = context.getSharedPreferences(SPEECT_STATUS, Context.MODE_PRIVATE);
        Boolean status = prfs.getBoolean("status", false);
        return status;
    }

    public boolean deleteAllData() {
        deleteScheduler();
        deleteCurrentTime();
        deletescheduleTimeForSelecteda();

        return true;
    }

}
