package com.splitur.app.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.splitur.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;

public final class Constants {


   public static String URL_BASE = "https://famger.com/";
   // public static String URL_BASE = "http://192.168.100.3:4000/";

    public static String IMG_PATH = "https://famger.com";

    public static String DEVICE_TOKEN = "";

    public static String GROUP_TITLE = "";
    public static String COST = "";
    public static Boolean VISIBILITY = false;
    public static String SLOTS = "";
    public static String OTP = "";
    public static String NUMBER = "";
    public static String EARNED_COINS = "";

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
        String outputPattern = "dd-MMM-yyyy";
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

    public static String getDate1(String data) {
        String date;
        String[] date_value = data.split("T");
        date = date_value[0];
        String formatted_date = formatDate1(date);
        formatted_date = formatted_date.replaceAll("-", " ");
        return formatted_date;
    }

    public static String formatDate1(String earningPeriod) {
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

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DATE, 30);
        String dateAfter = sdf.format(calendar.getTime());

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getTime1(String data) {

        String time;
        String[] time_value = data.split("T");
        time = time_value[1].substring(0, Math.min(time_value[1].length(), 8));

        String time1 = "";
        try {
            SimpleDateFormat t24 = new SimpleDateFormat("HH:mm");
            SimpleDateFormat t12 = new SimpleDateFormat("hh:mm aaa");
            Date date = t24.parse(time);
            time1 = t12.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return time1;
        }

        public static int getCategoryIcon (Context requireContext,int id){
            int icon;
            switch (id) {
                case 1:
                    icon = R.drawable.movies;
                    break;
                case 2:
                    icon = R.drawable.music;
                    break;
                case 3:
                    icon = R.drawable.vpn;
                    break;
                case 4:
                    icon = R.drawable.cloud;
                    break;
                case 5:
                    icon = R.drawable.games;
                    break;
                case 6:
                    icon = R.drawable.others;
                    break;
                default:
                    icon = R.color.images_placeholder;
            }
            return icon;
        }

        public static int getAvatarIcon (Context requireContext,int id){
            int icon;
            switch (id) {
                case 1:
                    icon = R.drawable.avatar_asian_man;
                    break;
                case 2:
                    icon = R.drawable.avatar_indian_man;
                    break;
                case 3:
                    icon = R.drawable.avatar_black_man;
                    break;
                case 4:
                    icon = R.drawable.avatar_western_man;
                    break;
                case 5:
                    icon = R.drawable.avatar_college_student;
                    break;
                case 6:
                    icon = R.drawable.avatar_grand_father;
                    break;
                case 7:
                    icon = R.drawable.avatar_asia_woman;
                    break;
                case 8:
                    icon = R.drawable.avatar_black_woman;
                    break;
                case 9:
                    icon = R.drawable.avatar_west_woman;
                    break;
                case 10:
                    icon = R.drawable.avatar_muslim_woman;
                    break;
                case 11:
                    icon = R.drawable.avatar_nerd_woman;
                    break;
                case 12:
                    icon = R.drawable.avatar_grand_mother;
                    break;
                case 13:
                    icon = R.drawable.avatar_farmer;
                    break;
                case 14:
                    icon = R.drawable.avatar_teacher;
                    break;
                case 15:
                    icon = R.drawable.avatar_astronaut;
                    break;
                case 16:
                    icon = R.drawable.avatar_doctor;
                    break;
                case 17:
                    icon = R.drawable.avatar_designer;
                    break;
                case 18:
                    icon = R.drawable.avatar_artist;
                    break;
                case 19:
                    icon = R.drawable.avatar_professor;
                    break;
                case 20:
                    icon = R.drawable.avatar_baby;
                    break;
                default:
                    icon = R.color.images_placeholder;
            }
            return icon;
        }


        public static void getApiError (Split context, ResponseBody errorBody){
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
//                Dashboard.showServerDown(jObjError.getString("message"));

                    Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_SHORT).show();
//                showServerDown(jObjError.getString("message"),getActivity(context));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        public static void getApiError1 (Activity context, ResponseBody errorBody){
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
                    Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_SHORT).show();
//                showServerDown(jObjError.getString("message"),context);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        private static void showServerDown (String message, Activity context){
            try {

                AlertDialog.Builder dialogBuilder;
                AlertDialog alertDialog;
                dialogBuilder = new AlertDialog.Builder(context);
                dialogBuilder.setCancelable(false);
                View layoutView = LayoutInflater.from(context).inflate(R.layout.server_down_dialogue, null);

                TextView error_msg = layoutView.findViewById(R.id.error_message);
                error_msg.setText(message);

                dialogBuilder.setView(layoutView);
                alertDialog = dialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimations;
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
            } catch (NullPointerException e) {
                Log.e("400", e.getMessage());
            }

        }


        public static Activity getActivity (Context context){
//        if (context == null)
//        {
//            return null;
//        }
//        else if (context instanceof ContextWrapper)
//        {
//            if (context instanceof Activity)
//            {
//                return (Activity) context;
//            }
//            else
//            {
//                return getActivity(((ContextWrapper) context).getBaseContext());
//            }
//        }
//
//        return null;

            Activity activity = (Activity) context;

            return activity;
        }


        public static String capitalize (String capString){
            StringBuffer capBuffer = new StringBuffer();
            Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
            while (capMatcher.find()) {
                capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
            }

            return capMatcher.appendTail(capBuffer).toString();
        }

    public static String getDateFromMiliSeconds(int time) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time * 1000L);
        String date = android.text.format.DateFormat.format("KK:mm aaa", calendar).toString();

        return date;
    }

    public static String getDateFromMiliSeconds1(int milliSeconds) {
        DateFormat formatter = new SimpleDateFormat("HH:mm aaa");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
