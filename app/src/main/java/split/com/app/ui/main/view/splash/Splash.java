package split.com.app.ui.main.view.splash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import split.com.app.R;
import split.com.app.databinding.ActivitySplashBinding;
import split.com.app.ui.main.view.otp_phone_number.OtpNumber;
import split.com.app.utils.ActivityUtil;

public class Splash extends AppCompatActivity {

    public static final int SMS_PERMISSION_REQ_CODE = 1221;
    ActivitySplashBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(binding.getRoot());

        binding.getStartedBtn.setOnClickListener(view -> {

            requestSmsPermission();

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestSmsPermission() {
        if (checkSelfPermission(Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, SMS_PERMISSION_REQ_CODE);
        } else {
            //permission already granted
            navToOtpScreen();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_REQ_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                navToOtpScreen();
            } else {
                Toast.makeText(this, "SMS permission is required!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void navToOtpScreen() {
        ActivityUtil.gotoPage(Splash.this, OtpNumber.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}