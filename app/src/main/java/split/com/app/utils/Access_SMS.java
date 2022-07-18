package split.com.app.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

import split.com.app.R;

public class Access_SMS {
        @TargetApi(Build.VERSION_CODES.M)
        static void getOTP(final Activity mActivity, final int PERMISSION_REQUEST_CODE)
        {
            List<String> permissionsNeeded = new ArrayList<>();

            List<String> permissionsList = new ArrayList<>();

            if (!addPermission(mActivity,permissionsList, android.Manifest.permission.READ_SMS))
                permissionsNeeded.add("READ_SMS");


            if (permissionsList.size() > 0)
            {
                mActivity.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), PERMISSION_REQUEST_CODE);
            }
        }

        @TargetApi(Build.VERSION_CODES.M)
        private static boolean addPermission(Context context, List<String> permissionsList, String permission)
        {
            if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
            {
                permissionsList.add(permission);

                return false;
            }
            return true;
        }
    }
