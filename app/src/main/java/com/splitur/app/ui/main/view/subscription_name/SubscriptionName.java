package com.splitur.app.ui.main.view.subscription_name;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.splitur.app.R;
import com.splitur.app.databinding.FragmentSubscriptionNameBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.utils.Constants;


public class SubscriptionName extends Fragment {

    FragmentSubscriptionNameBinding binding;
    String verification_type = "auth";
    boolean isOtp , isAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSubscriptionNameBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

        binding.subNameToolbar.title.setText("Name");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initClickListeners();
    }

    private void initClickListeners() {
//        binding.authLayout.setOnClickListener(view -> {
//            binding.authLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
//            binding.otpLayout.setBackgroundResource(R.drawable.unselected_bg);
//        });

        binding.otpLayout.setOnClickListener(view -> {
            if (binding.otpLayout.getTag().equals("unselected")){
                binding.otpLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
                binding.otpLayout.setTag("selected");
                isOtp = true;
            }else {
                binding.otpLayout.setBackgroundResource(R.drawable.unselected_bg);
                binding.otpLayout.setTag("unselected");
                isOtp = false;

            }
        });

        binding.authLayout.setOnClickListener(view -> {
            if (binding.authLayout.getTag().equals("unselected")){
                binding.authLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
                binding.authLayout.setTag("selected");
                isAuth = true;

            }else {
                binding.authLayout.setBackgroundResource(R.drawable.unselected_bg);
                binding.authLayout.setTag("unselected");
                isAuth = false;
            }
        });

        binding.BTNSUBNEXT.setOnClickListener(view -> {
            String title = binding.edGroupTitle.getText().toString().trim();
            if (!title.isEmpty()){
                //verification_type = checkVerificationType();
                verification_type = "auth";
                if (!verification_type.isEmpty()){

                    Constants.SUB_CAT_TITLE = title;
                    Constants.GROUP_TITLE = Constants.SUB_CAT_TITLE;
                    Constants.VALIDATION_TYPE = verification_type;
                    Navigation.findNavController(view).navigate(R.id.action_subscriptionName_to_slots);
                }
            }else {
                Toast.makeText(requireContext(), "Group Title Required", Toast.LENGTH_SHORT).show();
            }
        });

        binding.subNameToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

    }

    private String checkVerificationType() {
        if (isAuth && isOtp){
            verification_type = "auth/otp";
        }else if (isAuth){
            verification_type = "auth";
        }else if (isOtp){
            verification_type = "otp";
        }else {
            Toast.makeText(requireContext(), "Verification selection is required", Toast.LENGTH_SHORT).show();
            verification_type = "";
        }
        return verification_type;
    }
}