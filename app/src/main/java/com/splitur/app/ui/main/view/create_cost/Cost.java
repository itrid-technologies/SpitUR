package com.splitur.app.ui.main.view.create_cost;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

        binding.costValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int data = Integer.parseInt(String.valueOf(s.length()));
                if (data > 3) {
                    binding.errorMessage.setVisibility(View.VISIBLE);
                    binding.errorMessage.setText("Max cost is 999");
                    binding.btnNext.setEnabled(false);
                    binding.btnNext.setBackgroundColor(R.color.hint_color);
                }else {
                    binding.errorMessage.setVisibility(View.GONE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        binding.costProfile.userImage.setImageResource(Constants.getAvatarIcon(requireContext(), Integer.parseInt(avatar)));
    }

    private void initClickListeners() {
        binding.costToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.btnNext.setOnClickListener(view -> {
            String cost = binding.costValue.getText().toString().trim();

            final boolean isValid = validateCost(cost);

            if (isValid) {
                Constants.COST = cost;
                String validation_type = Constants.VALIDATION_TYPE;
                Navigation.findNavController(view).navigate(R.id.action_cost2_to_credentials2);

//                    Navigation.findNavController(view).navigate(R.id.action_cost2_to_credentials2);
//                } else {
//
//                    Navigation.findNavController(view).navigate(R.id.action_cost2_to_credentials2);
//
////                    if (validation_type.equalsIgnoreCase("otp")) {
////
////                        Navigation.findNavController(view).navigate(R.id.action_cost2_to_phoneCredentials);
////                    } else {
////                        Navigation.findNavController(view).navigate(R.id.action_cost2_to_credentials2);
////                    }
            }
        });
    }

    private boolean validateCost(String cost) {
        if (cost.isEmpty()) {
            cost = "0";
        }
        final int costPerMember = Integer.parseInt(cost);
        if (costPerMember == 0) {
            binding.errorMessage.setVisibility(View.VISIBLE);
            binding.errorMessage.setText("Enter cost per member");
            return false;
        } else if (costPerMember < 20) {
            binding.errorMessage.setText("20 required");
            binding.errorMessage.setVisibility(View.VISIBLE);
            return false;
        }else {
            return true;
        }
    }
}