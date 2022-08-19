package com.splitur.app.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class RazorPayed {
    WebView webView;
    Activity activity;
    public static final int UPI_INTENT_REQUEST_CODE = 101;
    private static final String UPI_URL_SCHEMA = "upi://pay";

    public RazorPayed(String key, WebView webView, Activity activity) {
        this.webView = webView;
        this.activity = activity;
        this.init();
    }

    private RazorPayed() {
    }

    private void init() {
        this.webView.addJavascriptInterface(this, "CheckoutBridge");
        setWebViewSettings(this.webView);
    }

    @JavascriptInterface
    public void getUpiIntentData() {
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
               sendIntentDataToWebView();
            }
        });
    }

    private void sendIntentDataToWebView() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("upi_intent_data", getUpiIntentsDataInJsonArray(this.activity));
            this.webView.loadUrl(String.format("javascript: upiIntentData(%s)", jsonObject.toString()));
        } catch (Exception var2) {
        }

    }

    @JavascriptInterface
    public void callNativeIntent(String url) {
        this.callNativeIntent(url, (String)null);
    }

    @JavascriptInterface
    public void callNativeIntent(final String url, final String packageName) {
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                Intent i = new Intent("android.intent.action.VIEW");
                i.setData(Uri.parse(url));
                if (packageName != null && packageName.length() > 0) {
                    i.setPackage(packageName);
                }


                activity.startActivityForResult(i, 101);
            }
        });
    }

    private static void setWebViewSettings(WebView webView) {
        setBaseWebViewSettings();
        enableJavaScriptInWebView(webView);
    }

    private static void setBaseWebViewSettings() {
        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private static void enableJavaScriptInWebView(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    private static JSONArray getUpiIntentsDataInJsonArray(Context context) {
        List<ResolveInfo> resolveInfoList = getListOfAppsWhichHandleDeepLink(context, "upi://pay");
        if (resolveInfoList != null && resolveInfoList.size() > 0) {
            JSONArray jsonArray = new JSONArray();
            Iterator var3 = resolveInfoList.iterator();

            while(var3.hasNext()) {
                ResolveInfo resolveInfo = (ResolveInfo)var3.next();
                jsonArray.put(getIntentDataInJson(context, resolveInfo));
            }

            return jsonArray;
        } else {
            return null;
        }
    }

    private static JSONObject getIntentDataInJson(Context context, ResolveInfo resolveInfo) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("package_name", resolveInfo.activityInfo.packageName);
            String appName = getAppNameOfResolveInfo(resolveInfo, context);
            jsonObject.put("app_name", appName);
            jsonObject.put("app_icon", getBase64FromOtherAppsResource(context, resolveInfo.activityInfo.packageName));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return jsonObject;
    }

    private static String getBase64FromOtherAppsResource(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();

        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            Resources resources = pm.getResourcesForApplication(applicationInfo);
            int appIconResId = applicationInfo.icon;
            return getBase64FromResource(resources, appIconResId);
        } catch (PackageManager.NameNotFoundException var6) {
            return null;
        }
    }

    private static String getBase64FromResource(Resources resources, int recourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, recourceId);
        if (bitmap != null) {
            String base64 = "data:image/png;base64,";
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            base64 = base64 + Base64.encodeToString(byteArray, 2);
            return base64;
        } else {
            return null;
        }
    }

    private static List<ResolveInfo> getListOfAppsWhichHandleDeepLink(Context context, String url) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        return context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
    }

    private static String getAppNameOfResolveInfo(ResolveInfo resolveInfo, Context context) throws Exception {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo applicationInfo = pm.getApplicationInfo(resolveInfo.activityInfo.packageName, PackageManager.GET_META_DATA);
            int stringId = applicationInfo.labelRes;
            Resources resources = pm.getResourcesForApplication(applicationInfo);
            String appName = stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : resources.getString(stringId);
            return appName;
        } catch (Exception var7) {
            var7.printStackTrace();
            throw var7;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101) {
            JSONObject jsonObject = new JSONObject();
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Set<String> keys = bundle.keySet();
                    Iterator var7 = keys.iterator();

                    while(var7.hasNext()) {
                        String key = (String)var7.next();

                        try {
                            jsonObject.put(key, bundle.get(key));
                        } catch (JSONException var10) {
                        }
                    }
                }
            }

            this.webView.loadUrl(String.format("javascript: pollStatus(%s)", jsonObject.toString()));
        }

    }
}
