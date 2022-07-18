package split.com.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

    private Context context;

    public MySharedPreferences(Context context) {
        this.context = context;
    }

    public void saveData(Context context, String key, String value) {

        SharedPreferences sharedPref = (SharedPreferences) context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();

    }

    public void saveBooleanData(Context context, String key, boolean value) {

        SharedPreferences sharedPref = (SharedPreferences) context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();

    }

    public String getData(Context context, String key) {

        SharedPreferences sharedPref = (SharedPreferences) context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPref.getString(key,"");

    }

    public boolean getBooleanData(Context context, String key) {

        SharedPreferences sharedPref = (SharedPreferences) context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key,false);

    }

    public void  clearData(){
        SharedPreferences sharedPref = (SharedPreferences) context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        sharedPref.edit().clear().apply();
    }

}

