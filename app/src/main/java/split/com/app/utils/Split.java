package split.com.app.utils;

import android.app.Application;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

public class Split extends Application {


    private static Split _splitInstance;

    public static Split getAppContext() {
        return _splitInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        _splitInstance = this;

        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String token = preferences.getData(Split.getAppContext(), "userAccessToken");

        if (!token.isEmpty()){
            ActivityUtil.gotoHome(this);
            Log.e("Access Token", token);
        }

        getDeviceToken();

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
}
