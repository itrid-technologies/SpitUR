package split.com.app.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Constants {


    public static String URL_BASE = "http://3.6.7.161:4000/";
//     public static String URL_BASE = "http://192.168.100.5:4000/";

    public static String IMG_PATH = "http://3.6.7.161:4000";
//    public static String IMG_PATH = "http://192.168.100.5:4000";
    public static String DEVICE_TOKEN = "";


    public static String GROUP_TITLE = "";
    public static String COST = "";
    public static Boolean VISIBILITY = false;
    public static String SLOTS = "";
    public static String OTP = "";
    public static String NUMBER = "";
    public static String EMAIL = "";
    public static String PASSWORD = "";
    public static String VALIDATION_TYPE = "";
    public static String PLAN_ID = "";
    public static String SUB_CAT_TITLE = "";
    public static String SUB_CATEGORY_ID = "";
    public static String VISIBILITY_string = "";
    public static String JoinEmail = "";

    //userDETAILS

    public static String USER_NAME = "";
    public static String USER_EMAIL = "";
    public static String ID = "";
    public static String USER_ID = "";
    public static String USER_AVATAR = "";
    public static String USER_NUMBER = "";


    public static String getDate(String data) {
        String date;
        String[] date_value = data.split("T");
        date = date_value[0];
        String formatted_date = formatDate(date);
        formatted_date = formatted_date.replaceAll("-", " ");
        return formatted_date;
    }

    public static String formatDate(String earningPeriod) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MMM";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(earningPeriod);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getTime(String data) {
        String time;
        String[] time_value = data.split("T");
        time = time_value[1].substring(0, Math.min(time_value[1].length(), 5));
        return time;
    }
}
