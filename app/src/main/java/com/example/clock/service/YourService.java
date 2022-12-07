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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.clock.screens.MainActivity;
import com.example.clock.shared_preference.LocalDataBase;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;


public class YourService extends Service {
    public int counter = 0;
    public static boolean isTrue = true;
    String speechTime;
    LocalDataBase localDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        localDatabase = new LocalDataBase(this);
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
        localDatabase = new LocalDataBase(this);


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
        Log.i("serviceDestroy", "==============yes");
        this.sendBroadcast(broadcastIntent);
    }


    private Timer timer;
    private TimerTask timerTask;

    public void startTimer() {
        timer = new Timer();

        timerTask = new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void run() {
                 String speechTime = null;
                int customMinute = getMinitFromTime(localDatabase.getCurrentTime(), localDatabase.getScheduler());
                int customHour = getHour(localDatabase.getCurrentTime());
                if (customMinute >= 60) {

                    int gg = customMinute - 60;

                    if (gg > 0 && customHour == 12) {
                        customHour = 0;
                        speechTime = "0" + getSpeechTime(customHour, 1) + "." + getMin(gg);
                    }
                    if ((customHour == 11 & gg > 0) || (customHour == 10 & gg > 0) || (customHour == 9 & gg > 0)) {
                        speechTime = getSpeechTime(customHour, 1) + "." + getMin(gg);
                    }
                    if ((customHour == 2 & gg > 0) || (customHour == 3 & gg > 0) || (customHour == 4 & gg > 0) || (customHour == 5 & gg > 0) || (customHour == 6 & gg > 0) || (customHour == 7 & gg > 0) || (customHour == 8 & gg > 0)) {
                        speechTime = "0" + getSpeechTime(customHour, 1) + "." + getMin(gg);
                    }

                    if (customHour == 12 && gg == 0) {
                        customHour = 0;
                        speechTime = "0" + getSpeechTime(customHour, 1) + "." + "00";
                    }

                    if ((customHour == 11 & gg == 0) || (customHour == 10 & gg == 0) || (customHour == 9 & gg == 0)) {
                        speechTime = getSpeechTime(customHour, 1) + "." + "00";
                    }

                    if ((customHour == 2 & gg == 0) || (customHour == 3 & gg == 0) || (customHour == 4 & gg == 0) || (customHour == 5 & gg == 0) || (customHour == 6 & gg == 0) || (customHour == 7 & gg == 0) || (customHour == 8 & gg == 0)) {
                        speechTime = "0" + getSpeechTime(customHour, 1) + "." + "00";
                    }
                }

                if (customMinute < 60) {
                    String hour = null, min = null;

                    hour = "" + customHour;
                    min = "" + customMinute;

                    if (customHour <= 9) {
                        hour = "0" + customHour;
                    }


                    if (customMinute <= 9) {
                        min = "0" + customMinute;
                    }

                    if (hour != null && min != null) {
                        speechTime = hour + "." + min;
                    }
                }


                if (speechTime != null) {
                  try {
                      if (localDatabase.getSpeechStatus() == true) {
                           if (CurrentTime.getCurrentDate3() == 24 || CurrentTime.getCurrentDate3() < 6) {
                              if (speechTime.equals(CurrentTime.getCurrentDate())) {
                                  int a = localDatabase.getScheduler() + localDatabase.getSchedulerForSelected();
                                  localDatabase.saveScheduler(String.valueOf(a));
                              }
                              return;
                          }
                      }
                      if (speechTime.equals(CurrentTime.getCurrentDate())) {
                          int a = localDatabase.getScheduler() + localDatabase.getSchedulerForSelected();
                          localDatabase.saveScheduler(String.valueOf(a));
                          MainActivity.textToSpeech();
                       }
                  }catch (Exception e){}
                }

            }
        };
        timer.schedule(timerTask, 1000, 10000); //

    }


    private String getMin(int gg) {
        if (gg > 9) {
            return String.valueOf(gg);
        } else {
            return "0" + gg;
        }
    }

    private Integer getSpeechTime(int customHour, int customMinit) {
        return Integer.parseInt(String.valueOf(customHour)) + Integer.parseInt(String.valueOf(customMinit));
    }

    private Integer getHour(String val) {
         String hour = null;

        if (val.length() == 4) {
            hour = val.charAt(0) + "";
        }

        if (val.length() == 5) {
            hour = val.charAt(0) + "" + val.charAt(1) + "";
        }


        return Integer.parseInt(hour);

    }

    private Integer getMinitFromTime(String val, int setTime) {

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
        try {
            int minit = setTime + Integer.parseInt(finalMin);

            if (minit >= 60) {
                return minit;
            }
            return minit;
        } catch (Exception e) {
            Log.i("ServiceException", e.getMessage());
        }


        return 0;
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