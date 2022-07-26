package com.splitur.app.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeAgo {
    public String covertTimeToText(String userLastSeen) {

        String convertTime = null;
        String suffix = "ago";

        try {

            final String format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());

            Date pasTime = dateFormat.parse(userLastSeen);

            String currentTimeStr = dateFormat.format(Calendar.getInstance().getTime());
            Date nowTime = dateFormat.parse(currentTimeStr);

            long dateDiff = getDateDiff(nowTime, pasTime);

            long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
            long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
            long hour = TimeUnit.MILLISECONDS.toHours(dateDiff);
            long day = TimeUnit.MILLISECONDS.toDays(dateDiff);

            if (second < 60 || minute < 60 || hour < 24) {
                convertTime = "Today";
//                if (second == 1) {
//                    convertTime = second + " second " + suffix;
//                } else {
//                    convertTime = second + " seconds " + suffix;
//                }
//            } else if (minute < 60) {
//                if (minute == 1) {
//                    convertTime = minute + " minute " + suffix;
//                } else {
//                    convertTime = minute + " minutes " + suffix;
//                }
//            } else if (hour < 24) {
//                if (hour == 1) {
//                    convertTime = hour + " hour " + suffix;
//                } else {
//                    convertTime = hour + " hours " + suffix;
//                }
            } else if (day >= 7) {
                if (day >= 365) {
                    long tempYear = day / 365;
                    if (tempYear == 1) {
                        convertTime = tempYear + " year " + suffix;
                    } else {
                        convertTime = tempYear + " years " + suffix;
                    }
                } else if (day >= 30) {
                    long tempMonth = day / 30;
                    if (tempMonth == 1) {
                        convertTime = (day / 30) + " month " + suffix;
                    } else {
                        convertTime = (day / 30) + " months " + suffix;
                    }
                } else {
                    long tempWeek = day / 7;
                    if (tempWeek == 1) {
                        convertTime = (day / 7) + " week " + suffix;
                    } else {
                        convertTime = (day / 7) + " weeks " + suffix;
                    }
                }
            } else {
                if (day == 1) {
                    convertTime = day + " day " + suffix;
                } else {
                    convertTime = day + " days " + suffix;
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("TimeAgo", e.getMessage() + "");
        }
        return convertTime;
    }

    /**
     * Get a diff between two dates
     *
     * @param oldDate the old date
     * @param newDate the new date
     * @return the diff value, in the days
     */
    private long getDateDiff(Date oldDate, Date newDate) {
        try {
            return TimeUnit.DAYS.convert(newDate.getTime() - oldDate.getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
