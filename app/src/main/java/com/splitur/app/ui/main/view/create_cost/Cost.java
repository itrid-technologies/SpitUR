package com.splitur.app.ui.main.view.create_cost;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import com.splitur.app.R;
import com.splitur.app.databinding.FragmentCostBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;


public class Cost extends Fragment {

    FragmentCostBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCostBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

        binding.costToolbar.title.setText("Cost");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initClickListeners();
        setProfileData();
    }

    private void setProfileData() {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String user_name = Constants.USER_NAME;
        String user_ID = Constants.USER_ID;
        String avatar = Constants.USER_AVATAR;
        String slot = Constants.SLOTS;

        binding.costProfile.netflix.setText(Constants.SUB_CAT_TITLE);
        binding.costProfile.userName.setText(String.format("@%s", user_ID));
        binding.costProfile.count.setText(slot + " Slots");
        Glide.with(Split.getAppContext()).load(avatar).into(binding.costProfile.userImage);
    }

    private void initClickListeners() {
        binding.costToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.btnNext.setOnClickListener(view -> {
            String cost = binding.costValue.getText().toString().trim();

            if (!cost.isEmpty()) {
                Constants.COST = cost;
                String validation_type = Constants.VALIDATION_TYPE;
                if (validation_type.isEmpty()) {
                    Navigation.findNavController(view).navigate(R.id.action_cost2_to_credentials2);
                } else {
                    if (validation_type.equalsIgnoreCase("otp")) {

                        Navigation.findNavController(view).navigate(R.id.action_cost2_to_phoneCredentials);
                    } else {
                        Navigation.findNavController(view).navigate(R.id.action_cost2_to_credentials2);
                    }
                }
            } else {
                binding.errorMessage.setVisibility(View.VISIBLE);
                binding.errorMessage.setText("Enter cost per member");
            }
        });
    }
}