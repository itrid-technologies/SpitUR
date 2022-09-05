package com.splitur.app.ui.main.view.join_plans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.bumptech.glide.Glide;
import com.splitur.app.R;
import com.splitur.app.databinding.ActivityCheckoutFailedBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.utils.Split;

import java.util.Locale;

public class CheckoutFailed extends AppCompatActivity {

    ActivityCheckoutFailedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutFailedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Glide.with(Split.getAppContext()).load(R.drawable.payment_failed).into(binding.paymentFailureGif);

        Dashboard.hideNav(true);
        binding.jcfToolbar.title.setText("Payment Failed");
        binding.jcfToolbar.back.setVisibility(View.GONE);

        clickListeners();
        startCountDown();

    }

    private void clickListeners() {
        binding.btnHome01.setOnClickListener(view -> {
            Intent intent = new Intent(CheckoutFailed.this,Dashboard.class);
            startActivity(intent);
            finishAffinity();
        });

    }

    private void startCountDown() {
        new CountDownTimer(300000, 1000) {

            public void onTick(long millisUntilFinished) {
                binding.retryCounter.setText(String.format(Locale.ENGLISH, "%d sec", (int) (millisUntilFinished / 1000)));
                binding.btnRetry.setEnabled(false);
            }

            @SuppressLint("ResourceAsColor")
            public void onFinish() {
                binding.retryCounter.setText("Resend");
                binding.retryCounter.setTextColor(Color.parseColor("#FFFFFFFF"));
                binding.btnRetry.setEnabled(true);
            }

        }.start();
    }
}