package com.example.clock.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.clock.screens.MainActivity;
import com.example.clock.shared_preference.LocalDataBase;

import java.util.Timer;
import java.util.TimerTask;


public class YourService extends Service {
    public int counter = 0;
    public static boolean isTrue = true;
    String speechTime;

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "example.permanence";
        String channelName = "Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stoptimertask();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);
    }


    private Timer timer;
    private TimerTask timerTask;

    public void startTimer() {
        timer = new Timer();

        timerTask = new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void run() {

                int customMinute = getMinitFromTime(LocalDataBase.getCurrentTime(), LocalDataBase.getScheduler());
                int customHour = getHour(LocalDataBase.getCurrentTime());

                if (isTrue == true) {
                    if (customMinute >= 60) {
                        customMinute = 1;
                        int gg = customMinute - 60;
                        if (gg > 0 && customHour == 12) {
                            customHour = 0;
                            speechTime = "0" + getSpeechTime(customHour, customMinute) + "." + getMin(gg);
                        }
                        if ((customHour == 11 & gg > 0) || (customHour == 10 & gg > 0) || (customHour == 9 & gg > 0)) {
                            speechTime = getSpeechTime(customHour, customMinute) + "." + getMin(gg);
                        }
                        if ((customHour == 2 & gg > 0) || (customHour == 3 & gg > 0) || (customHour == 4 & gg > 0) || (customHour == 5 & gg > 0) || (customHour == 6 & gg > 0) || (customHour == 7 & gg > 0) || (customHour == 8 & gg > 0)) {
                            speechTime = "0" + getSpeechTime(customHour, customMinute) + "." + getMin(gg);
                        }

                        if (customHour == 12 && gg == 0) {
                            customHour = 0;
                            speechTime = "0" + getSpeechTime(customHour, customMinute) + "." + "00";
                        }

                        if ((customHour == 11 & gg == 0) || (customHour == 10 & gg == 0) || (customHour == 9 & gg == 0)) {
                            speechTime = getSpeechTime(customHour, customMinute) + "." + "00";
                        }

                        if ((customHour == 2 & gg == 0) || (customHour == 3 & gg == 0) || (customHour == 4 & gg == 0) || (customHour == 5 & gg == 0) || (customHour == 6 & gg == 0) || (customHour == 7 & gg == 0) || (customHour == 8 & gg == 0)) {
                            speechTime = "0" + getSpeechTime(customHour, customMinute) + "." + "00";
                        }
                    }

                    if (customMinute < 60) {
                        String hour = null, min = null;
                        if (customHour < 9) {
                            hour = "0" + customHour;
                        }
                        if (customHour > 9) {
                            hour = "" + customHour;
                        }
                        if (String.valueOf(customMinute).length() > 2) {
                            min = "0" + customMinute;
                        }
                        if (String.valueOf(customMinute).length() == 2) {
                            min = "" + customMinute;
                        }
                        if (hour != null && min != null) {
                            speechTime = hour + "." + min;
                        }
                    }
                    isTrue = false;
                }
                if (speechTime != null) {
                    if (speechTime.equals(CurrentTime.getCurrentDate())) {
                        LocalDataBase.saveScheduler(LocalDataBase.getScheduler() + LocalDataBase.getSchedulerForSelected());
                        MainActivity.textToSpeech();
                        speechTime = null;
                        isTrue = true;
                    }
                }


                Log.i("Count", "=========  " + CurrentTime.getCurrentDate());
            }
        };
        timer.schedule(timerTask, 10000, 10000); //
    }

    private String getMin(int gg) {
        if (String.valueOf(gg).length() == 2) {
            return String.valueOf(gg);
        } else {
            return "0" + gg;
        }
    }

    private Integer getSpeechTime(int customHour, int customMinit) {
        return Integer.parseInt(String.valueOf(customHour)) + Integer.parseInt(String.valueOf(customMinit));
    }

    private Integer getHour(float scheduleSetTime) {
        String val = String.valueOf(scheduleSetTime);
        String hour = null;

        if (val.length() == 4) {
            hour = val.charAt(0) + "";
        }

        if (val.length() == 5) {
            hour = val.charAt(0) + "" + val.charAt(1) + "";
        }
        String a = String.format("%.0f", Float.parseFloat(hour));


        return Integer.parseInt(a);

    }

    private Integer getMinitFromTime(float scheduleSetTime, float setTime) {
        String val = String.valueOf(scheduleSetTime);
        String min1 = null, min2 = null, returnMinit, finalMin;

        if (val.length() == 4) {
            min1 = val.charAt(2) + "";
            min2 = val.charAt(3) + "";
        }

        if (val.length() == 5) {
            min1 = val.charAt(3) + "";
            min2 = val.charAt(4) + "";
        }

        finalMin = min1 + min2;
        float minit = setTime + Float.parseFloat(finalMin);

        if (minit >= 60) {
            return Math.round(minit);
        }

        //  String a = String.format("%.0f", minit);


        return Math.round(minit);


    }

    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}