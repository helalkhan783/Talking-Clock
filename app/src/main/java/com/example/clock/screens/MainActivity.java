package com.example.clock.screens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.clock.R;
import com.example.clock.handler.AlermHandler;
import com.example.clock.service.CurrentTime;
import com.example.clock.shared_preference.LocalDataBase;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    LocalDataBase localDataBase;
    private static TextToSpeech textToSpeech;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localDataBase = new LocalDataBase(this);
        initAll();
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.previous:
                    setDataToLocal(1);
                    break;
                case R.id.fiveMin:
                    setDataToLocal(5);
                    break;
                case R.id.fifteenMin:
                    setDataToLocal(15);
                    break;
                case R.id.thirtyMin:
                    setDataToLocal(30);
                    break;
                case R.id.sixtyMin:
                    setDataToLocal(60);
                    break;
            }

        });
        textToSpeechManage();

    }

    private void setDataToLocal(Integer scheduleTime) {
        localDataBase.saveScheduler(scheduleTime);
        setTime(localDataBase.getScheduler());
    }

    public void setTime(long triggerAfter) {
        try {
            AlermHandler alermHandler = new AlermHandler(this);
            alermHandler.cancel();
            alermHandler.setAlarmManager(triggerAfter);
        } catch (Exception e) {
        }
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
    public static void setDataToScheduler(String s) {
        int speechStatus = textToSpeech.speak(CurrentTime.getCurrentDate(), TextToSpeech.QUEUE_FLUSH, null);
        if (speechStatus == TextToSpeech.ERROR) {
            Log.e("TTS", "Error in converting Text to Speech!");
        }
    }

    private void initAll() {
        radioGroup = findViewById(R.id.radioGroud);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (textToSpeech != null) {
//            textToSpeech.stop();
//            textToSpeech.shutdown();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}