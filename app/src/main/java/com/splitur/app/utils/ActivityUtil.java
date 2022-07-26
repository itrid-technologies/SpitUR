package com.splitur.app.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import com.splitur.app.ui.main.view.dashboard.Dashboard;


public class ActivityUtil {


    public static void gotoPage(Context context, Class<?> activityClass) {
        context.startActivity(new Intent(context, activityClass));
    }

    public static void gotoPage(Context context, Class<?> activityClass, Bundle bundle) {
        Intent intent = new Intent(context, activityClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    public static void gotoPage(Context context, Class<?> activityClass, int flags) {
        Intent intent = new Intent(context, activityClass);
        intent.addFlags(flags);
        context.startActivity(intent);
    }

    public static void gotoHome(Context context) {
        Intent intent = new Intent(context, Dashboard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
