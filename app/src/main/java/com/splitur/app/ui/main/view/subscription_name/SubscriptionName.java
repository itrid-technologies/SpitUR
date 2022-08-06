package com.splitur.app.ui.main.view.subscription_name;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.splitur.app.R;
import com.splitur.app.databinding.FragmentSubscriptionNameBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.utils.Constants;


public class SubscriptionName extends Fragment {

    FragmentSubscriptionNameBinding binding;
    String verification_type = "auth";


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
            verification_type = "otp";
            binding.otpLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
            binding.authLayout.setBackgroundResource(R.drawable.unselected_bg);
        });

        binding.authLayout.setOnClickListener(view -> {
            verification_type = "auth";
            binding.otpLayout.setBackgroundResource(R.drawable.unselected_bg);
            binding.authLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
        });

        binding.BTNSUBNEXT.setOnClickListener(view -> {
            String title = binding.edGroupTitle.getText().toString().trim();
            if (!title.isEmpty()){
                Constants.SUB_CAT_TITLE = title;
                Constants.GROUP_TITLE = Constants.SUB_CAT_TITLE;

                Constants.VALIDATION_TYPE = verification_type;

//                MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
//                preferences.saveData(Split.getAppContext(),"GROUP_TITLE",title);
//                preferences.saveData(Split.getAppContext(),"VALIDATION_TYPE",verification_type);
                Navigation.findNavController(view).navigate(R.id.action_subscriptionName_to_slots);
            }
        });

        binding.subNameToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

    }
}