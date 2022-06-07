package split.com.app.utils;

import android.app.Application;
import android.util.Log;

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

    }
}
