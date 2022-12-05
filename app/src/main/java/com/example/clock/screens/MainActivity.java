package com.example.clock.screens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ActivityManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.clock.R;
import com.example.clock.databinding.ActivityMainBinding;
import com.example.clock.service.CurrentTime;
import com.example.clock.service.Restarter;
import com.example.clock.service.YourService;
import com.example.clock.shared_preference.LocalDataBase;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private Intent mServiceIntent;
    private YourService mYourService;

    private static TextToSpeech textToSpeech;
    public static Context context;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = this;
        // if one time radio btn is selected
        selectedRadioBtn();
        // initiate text to speech engine
        textToSpeechManage();
        binding.radioGroud.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.previous:
                    startServiceAndScheduleSetToLocal(1f);
                    break;
                case R.id.fiveMin:
                    startServiceAndScheduleSetToLocal(5f);
                    break;
                case R.id.fifteenMin:
                    startServiceAndScheduleSetToLocal(15f);
                    break;
                case R.id.thirtyMin:
                    startServiceAndScheduleSetToLocal(30f);
                    break;
                case R.id.sixtyMin:
                    startServiceAndScheduleSetToLocal(60f);
                    break;
            }

        });
    }


    private void selectedRadioBtn() {
        if (LocalDataBase.getSchedulerForSelected() > 0) {
            if (LocalDataBase.getSchedulerForSelected() == 1) {
                binding.radioGroud.check(binding.previous.getId());
            }
            if (LocalDataBase.getSchedulerForSelected() == 5) {
                binding.radioGroud.check(binding.fifteenMin.getId());
            }
            if (LocalDataBase.getSchedulerForSelected() == 15) {
                binding.radioGroud.check(binding.fifteenMin.getId());
            }
            if (LocalDataBase.getSchedulerForSelected() == 30) {
                binding.radioGroud.check(binding.thirtyMin.getId());
            }
            if (LocalDataBase.getSchedulerForSelected() == 60) {
                binding.radioGroud.check(binding.sixtyMin.getId());
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startServiceAndScheduleSetToLocal(float v) {

        LocalDataBase.scheduleTimeForSelected(v);//for selected
        LocalDataBase.setCurrentTime(Float.parseFloat(CurrentTime.getCurrentDate()));
        LocalDataBase.saveScheduler(v);
        mYourService = new YourService();
        mServiceIntent = new Intent(this, mYourService.getClass());
       // Toast.makeText(MainActivity.this, ""+LocalDataBase.getSchedulerForSelected(), Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!isMyServiceRunning(mYourService.getClass())) {
                startForegroundService(mServiceIntent);
            }

        } else {
            if (!isMyServiceRunning(mYourService.getClass())) {
                startService(mServiceIntent);
            }
        }


    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("Service status", "Running");
                return true;
            }
        }
        Log.i("Service status", "Not running");
        return false;
    }


    @Override
    protected void onDestroy() {
       //  stopService(mServiceIntent);
        restartSerVice();
        super.onDestroy();
    }




    @Override
    protected void onStop() {
        restartSerVice();
        super.onStop();
    }

    private void textToSpeechManage() {
        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                int ttsLang = textToSpeech.setLanguage(new Locale("bn_IN"));
                if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                        || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "The Language is not supported!");
                } else {
                    Log.i("TTS", "Language Supported.");
                }
                Log.i("TTS", "Initialization success.");
            } else {
                Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void textToSpeech() {
        int speechStatus = textToSpeech.speak(CurrentTime.getCurrentDate1(), TextToSpeech.QUEUE_FLUSH, null);
        if (speechStatus == TextToSpeech.ERROR) {
            Log.e("TTS", "Error in converting Text to Speech!");
        }
    }

    private void restartSerVice() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);

    }
}