package com.splitur.app.utils;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.messaging.FirebaseMessaging;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.view.splash.Splash;

public class Split extends Application {


    private static Split _splitInstance;

    public static Split getAppContext() {
        return _splitInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        _splitInstance = this;

        //disable dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        if (!token.isEmpty()){
            ActivityUtil.gotoHome(this);
            Log.e("Access Token", token);
        }


        getDeviceToken();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });

//        if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
//        } else {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }

//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
//        {
//            //To re-enable full screen:
//
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//        }
//        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
//        {
//            //To disable full screen:
//
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
    }

    private void getDeviceToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("FCM TOKEN", "onComplete: Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Get new FCM registration token
                    Constants.DEVICE_TOKEN = task.getResult();
                    Log.e("FCM TOKEN", Constants.DEVICE_TOKEN);
                });
    }

//    @Override //reconfigure display properties on screen rotation
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        //Checks the orientation of the screen
//        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
//        {
//            // handle change here
//        }
//        else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
//        {
//            // or here
//        }
//    }
}
