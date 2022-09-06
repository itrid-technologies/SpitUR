package com.splitur.app.ui.main.view.group_created_and_joined;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.NonUiContext;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.splitur.app.R;
import com.splitur.app.databinding.FragmentCheckoutFailedBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.view.join_plans.CheckoutActivity;
import com.splitur.app.ui.main.viewmodel.CheckOutViewModel;
import com.splitur.app.utils.Split;

import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class CheckoutFailed extends Fragment {


    FragmentCheckoutFailedBinding binding;
    String groupId, upiId, subscriptionId, groupCredentials;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutFailedBinding.inflate(inflater, container, false);

        Dashboard.hideNav(true);
        binding.jcfToolbar.title.setText("Payment Failed");
        binding.jcfToolbar.back.setVisibility(View.GONE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null){
            groupId = getArguments().getString("group_id");
            upiId = getArguments().getString("upi_id");
            subscriptionId = getArguments().getString("subscription_id");
            groupCredentials = getArguments().getString("group_credentials");
        }

        Glide.with(Split.getAppContext()).load(R.drawable.payment_failed).into(binding.paymentFailureGif);

        clickListeners();
        startCountDown();

    }
    private void clickListeners() {
        binding.btnHome01.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.home2);
        });

        binding.btnRetry.setOnClickListener(view -> {
            CheckOutViewModel checkOutViewModel = new CheckOutViewModel();
            checkOutViewModel.init();
            checkOutViewModel.getData().observe(getViewLifecycleOwner(), secretKeyModel -> {
                if (secretKeyModel.isStatus()) {
                    String secret_key = secretKeyModel.getKey();

                    Intent checkoutIntent = new Intent(requireContext(), CheckoutActivity.class);
                    checkoutIntent.putExtra("group_id",groupId);
                    checkoutIntent.putExtra("upi_id", upiId);
                    checkoutIntent.putExtra("subscription_id", subscriptionId);
                    checkoutIntent.putExtra("group_credentials", groupCredentials);
                    checkoutIntent.putExtra("secret_key", secret_key);
                    startActivity(checkoutIntent);
                    requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }
            });
        });

    }

    private void startCountDown() {
        new CountDownTimer(300000, 1000) {

            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            public void onTick(long millisUntilFinished) {
             //   binding.retryCounter.setText(String.format(Locale.ENGLISH, "%d sec", (int) (millisUntilFinished / 1000)));
                binding.retryCounter.setText(""+String.format("%d:%d ",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                binding.btnRetry.setEnabled(false);
            }

            @SuppressLint("ResourceAsColor")
            public void onFinish() {
                binding.retryCounter.setText("0:0");
                binding.retryCounter.setTextColor(Color.parseColor("#FFFFFFFF"));
                binding.btnRetry.setEnabled(true);
            }

        }.start();
    }
}