package com.example.nakama.searchproduct.util;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Util {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static long datetimeStringToMilis(String datetime) {
        long result = 0L;

        try {
            LocalDate localDate = LocalDate.parse(datetime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            result = date.getTime();
        } catch (Exception ex) {
            Log.d("Util", "Error datetimeStringToMilis - "+ex.getLocalizedMessage());
        }

        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String convertDateTimeToFormattedTime(String datetime) {
        StringBuilder result = new StringBuilder();

        long currentDateTimeMillis = System.currentTimeMillis();

        try {
            long milis = datetimeStringToMilis(datetime);
            long time = 0;
            if (currentDateTimeMillis > milis) {
                time = currentDateTimeMillis - milis;
            }
            result.append(milisToTimeMessage(time));

            if (result.toString().trim().equals("")) {
                result.append(datetime);
            }
        } catch (Exception ex) {
            Log.d("Util", "Error convertDateTimeToFormattedTime - "+ex.getLocalizedMessage());
        }

        return result.toString();
    }

    public static String milisToTimeMessage(long duration) {
        StringBuilder result = new StringBuilder();

        int seconds = 0;
        if (duration >= 1000) {
            seconds = (int) (duration / 1000);
        }
        int minutes = 0;
        if (seconds >= 60) {
            minutes = (int) (seconds / 60);
        }

        int hours = 0;
        if (minutes >= 60) {
            hours = (int) (minutes / 60);
        }

        int days = 0;
        if (hours >= 24) {
            days = (int) (hours / 24);
        }

        int months = 0;
        if (days >= 30) {
            months = (int) (days / 30);
        }

        int years = 0;
        if (days >= 365) {
            years = (int) (days % 365);
        }

		if (years > 0) {
			result.append(years + " year" + (years > 1 ? "s" : ""));
		} else if (months > 0) {
			result.append(months + " month" + (months > 1 ? "s" : ""));
		} else if (days > 0) {
			result.append(days + " day" + (days > 1 ? "s" : ""));
		} else if (hours > 0) {
			result.append(hours + " hour" + (hours > 1 ? "s" : ""));
		} else {
			result.append(minutes + " minute" + (minutes > 1 ? "s" : ""));
		}

		return result.toString() + " ago";
    }
}
