package com.example.clock.handler;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import com.example.clock.service.ExecutableService;
import com.example.clock.shared_preference.LocalDataBase;

import java.util.Calendar;

public class AlermHandler {
    Context context;

    public AlermHandler(Context context) {
        this.context = context;

    }

    public void setAlarmManager(long triggerAfter) {

        try {
            AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, ExecutableService.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, 1000 * 60 * 1,
                    1000 * 60 * triggerAfter, alarmIntent);

            Toast.makeText(context, "set", Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            Toast.makeText(context, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void cancel() {
        Intent intent = new Intent(context, ExecutableService.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 2, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (am != null) {
            am.cancel(sender);
        }
    }
}
