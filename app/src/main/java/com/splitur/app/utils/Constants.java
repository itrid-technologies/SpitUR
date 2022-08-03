package com.splitur.app.utils;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.google.android.material.snackbar.Snackbar;
import com.splitur.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;

public final class Constants {


    public static String URL_BASE = "http://famger.com:4000/";
//     public static String URL_BASE = "http://3.110.227.193:4000/";

    public static String IMG_PATH = "http://famger.com:4000";
//        public static String IMG_PATH = "http://3.110.227.193:4000";
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

    public static String Referrer = "";
    public static int conversation_id = 0;
    public static String SourceId = "";
    public static int ContactId = 0;
    public static int AccountId = 0;
    public static String ChatApiKey = "";
    public static int InboxId = 0;


    public static final String FACEBOOK_PACKAGE_NAME = "com.facebook.katana";
    public static final String TWITTER_PACKAGE_NAME = "com.twitter.android";
    public static final String INSTAGRAM_PACKAGE_NAME = "com.instagram.android";
    public static final String PINTEREST_PACKAGE_NAME = "com.pinterest";
    public static final String WHATS_PACKAGE_NAME = "com.whatsapp";
    public static final String DISCORD_PACKAGE_NAME = "https://discord.gg";

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String coinsDate(String dateAdded) {

        String date;
        String[] date_value = dateAdded.split("T");
        date = date_value[0];
//        String inputPattern = "yyyy-mm-dd";
//        String outputPattern = "dd/mm/yyyy";
//        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
//        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
//        Date date1 = null;
//        String str = null;
//        String str1 = null;
////
//        try{
//            date1 = inputFormat.parse(date);
//            str = outputFormat.format(date1);
//        }catch(ParseException e){
//            e.printStackTrace();
//        }
//
//        Calendar c = Calendar.getInstance();
//        c.setTime(date1);
//        c.add(Calendar.DATE, 30);
//        date1 = c.getTime();
//        str = outputFormat.format(date1);
//
////        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
////        Calendar cal = Calendar.getInstance();
////        try{
////            cal.setTime(sdf.parse(dateAdded));
////            sdf.format(cal.getTime());
////        }catch(ParseException e){
////            e.printStackTrace();
////        }
////        cal.add(Calendar.DATE, 1);
////        String dateAfter = sdf.format(cal.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 30);
        String dateAfter = sdf.format(c.getTime());

        return dateAfter;
    }

    public static String getFormattedDateTimeNow() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        return sdf.format(Calendar.getInstance().getTime());
    }

    public static String getTime(String data) {
        String time;
        String[] time_value = data.split("T");
        time = time_value[1].substring(0, Math.min(time_value[1].length(), 5));
        return time;
    }

    public static int getCategoryIcon(Context requireContext, int id) {
        int icon;
        switch (id) {
            case 1:
                icon = R.drawable.movies;
                break;
            case 2:
                icon = R.drawable.cloud;
                break;
            case 3:
                icon = R.drawable.games;
                break;
            case 4:
                icon = R.drawable.music;
                break;
            case 5:
                icon = R.drawable.others;
                break;
            case 6:
                icon = R.drawable.vpn;
                break;
            default:
                icon = R.color.images_placeholder;
        }
        return icon;
    }

    public static int getAvatarIcon(Context requireContext, int id) {
        int icon;
        switch (id) {
            case 1:
                icon = R.drawable.movies;
                break;
            case 2:
                icon = R.drawable.cloud;
                break;
            case 3:
                icon = R.drawable.games;
                break;
            case 4:
                icon = R.drawable.music;
                break;
            case 5:
                icon = R.drawable.others;
                break;
            case 6:
                icon = R.drawable.vpn;
                break;
            case 7:
                icon = R.drawable.vpn;
                break;
            case 8:
                icon = R.drawable.vpn;
                break;
            case 9:
                icon = R.drawable.vpn;
                break;
            default:
                icon = R.color.images_placeholder;
        }
        return icon;
    }


    public static void getApiError(Split context, ResponseBody errorBody) {
        String data = null;
        try {
            data = errorBody.string();
            JSONObject jObjError = null;
            try {
                jObjError = new JSONObject(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                Toast.makeText(context, jObjError.getString("message"),
                        Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
