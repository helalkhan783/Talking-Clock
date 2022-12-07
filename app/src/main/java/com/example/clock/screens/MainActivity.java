package com.example.clock.screens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ActivityManager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.WindowManager;
import android.widget.CompoundButton;
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
    LocalDataBase localDataBase;
    private Intent mServiceIntent;
    private YourService mYourService;

    private static TextToSpeech textToSpeech;
    public static Context context;
    public static boolean isOk = true;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        localDataBase = new LocalDataBase(this);
        // if one time radio btn is selected
        selectedRadioBtn();
        // initiate text to speech engine
        textToSpeechManage();
        // manage service by switch


        binding.radioGroud.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.previous:
                    startServiceAndScheduleSetToLocal("1");
                    break;
                case R.id.fiveMin:
                    startServiceAndScheduleSetToLocal("5");
                    break;
                case R.id.fifteenMin:
                    startServiceAndScheduleSetToLocal("15");
                    break;
                case R.id.thirtyMin:
                    startServiceAndScheduleSetToLocal("30");
                    break;
                case R.id.sixtyMin:
                    startServiceAndScheduleSetToLocal("60");
                    break;


            }

        });

        binding.timeSpeechOff.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked == false) {
                localDataBase.timeSpeechStatusSet(false);
            } else {
                localDataBase.timeSpeechStatusSet(true);

            }
        });


    }


    private void selectedRadioBtn() {

        if (localDataBase.getSchedulerForSelected() > 0) {

            if (localDataBase.getSchedulerForSelected() == 1) {
                binding.radioGroud.check(binding.previous.getId());
            }
            if (localDataBase.getSchedulerForSelected() == 5) {
                binding.radioGroud.check(binding.fifteenMin.getId());
            }
            if (localDataBase.getSchedulerForSelected() == 15) {
                binding.radioGroud.check(binding.fifteenMin.getId());
            }
            if (localDataBase.getSchedulerForSelected() == 30) {
                binding.radioGroud.check(binding.thirtyMin.getId());
            }
            if (localDataBase.getSchedulerForSelected() == 60) {
                binding.radioGroud.check(binding.sixtyMin.getId());
            }
        }

        if (localDataBase.getSpeechStatus() == true) {
            binding.timeSpeechOff.setChecked(true);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startServiceAndScheduleSetToLocal(String v) {
        localDataBase.scheduleTimeForSelected(v);//for selected
        localDataBase.setCurrentTime(CurrentTime.getCurrentDate());
        localDataBase.saveScheduler(v);

        if (mYourService == null) {
            mYourService = new YourService();
        }
        binding.onOfSwitch.setChecked(true);


        mServiceIntent = new Intent(this, mYourService.getClass());

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
        // stopService(mServiceIntent);
        restartSerVice();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // stopService(mServiceIntent);
        restartSerVice();
        super.onPause();
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
        int speechStatus = textToSpeech.speak("এখন সময়, " + CurrentTime.getCurrentDate1(), TextToSpeech.QUEUE_FLUSH, null);
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