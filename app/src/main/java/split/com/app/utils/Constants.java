package split.com.app.utils;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import split.com.app.R;

public final class Constants {


    public static String URL_BASE = "http://3.109.223.84:4000/";
//     public static String URL_BASE = "http://192.168.100.5:4000/";

    public static String IMG_PATH = "http://3.109.223.84:4000";
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

    public static String coinsDate(String dateAdded) {
        String date;
        String[] date_value = dateAdded.split("T");
        date = date_value[0];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        try{
            cal.setTime(sdf.parse(date));
            sdf.format(cal.getTime());
        }catch(ParseException e){
            e.printStackTrace();
        }
        cal.add(1,1);
        String dateAfter = sdf.format(cal.getTime());
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
}
