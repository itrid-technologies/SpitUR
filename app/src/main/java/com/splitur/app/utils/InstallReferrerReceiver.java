package com.splitur.app.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class InstallReferrerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String referrer = intent.getStringExtra("referrer");

        Constants.Referrer = referrer;
    }
}