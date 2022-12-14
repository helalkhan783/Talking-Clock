package com.example.clock.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class CurrentTime {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
        String formattedDate = dateFormat.format(new Date()).toString();

        return dateFormatForPm(formattedDate);

    }

    private static String dateFormatForPm(String date) { //for this format (yyy-MM-dd hh:mma)
        String datetime = null;
        java.text.DateFormat inputFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
        SimpleDateFormat d = new SimpleDateFormat("hh.mm");
        try {
            Date convertedDate = inputFormat.parse(date);
            datetime = d.format(convertedDate);

        } catch (Exception e) {

        }
        return datetime;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getCurrentDate1() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
        String formattedDate = dateFormat.format(new Date()).toString();

        return dateFormatForPm1(formattedDate);

    }

    private static String dateFormatForPm1(String date) { //for this format (yyy-MM-dd hh:mma)
        String datetime = null;
        java.text.DateFormat inputFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
        SimpleDateFormat d = new SimpleDateFormat("hh.mm aa");
        try {
            Date convertedDate = inputFormat.parse(date);
            datetime = d.format(convertedDate);

        } catch (Exception e) {

        }
        return datetime;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Integer getCurrentDate3() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy HH");
        String formattedDate = dateFormat.format(new Date()).toString();

        return dateFormatForPm3(formattedDate);

    }

    private static Integer dateFormatForPm3(String date) { //for this format (yyy-MM-dd hh:mma)
        String datetime = null;
        java.text.DateFormat inputFormat = new SimpleDateFormat("dd-MMM-yy HH");
        SimpleDateFormat d = new SimpleDateFormat("HH");

        try {
            Date convertedDate = inputFormat.parse(date);
            datetime = d.format(convertedDate);

        } catch (Exception e) {

        }
        return Integer.parseInt(datetime);
    }


}
