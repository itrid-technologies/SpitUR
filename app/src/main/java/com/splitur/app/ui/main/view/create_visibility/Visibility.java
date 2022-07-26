package com.splitur.app.ui.main.view.create_visibility;

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
import com.splitur.app.databinding.FragmentVisibilityBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;


public class Visibility extends Fragment {


    FragmentVisibilityBinding binding;
    boolean visibility = true;
    int visible = 1;
    String slot;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVisibilityBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

        binding.visibilityToolbar.title.setText("Visibility");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clickListeners();

        setProfileData();

    }

    private void setProfileData() {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String user_name = Constants.USER_NAME;
        String user_ID = Constants.USER_ID;
        String avatar = Constants.USER_AVATAR;
        slot = Constants.SLOTS;
        if (!slot.isEmpty()) {
            Constants.SLOTS = slot;
            slot = Constants.SLOTS;

        } else {
            Constants.SLOTS = "4";
            slot = Constants.SLOTS;
        }

        binding.visibilityProfile.netflix.setText(Constants.SUB_CAT_TITLE);
        binding.visibilityProfile.userName.setText(String.format("@%s", user_ID));
        binding.visibilityProfile.count.setText(slot + " Slots");
        Glide.with(Split.getAppContext()).load(avatar).into(binding.visibilityProfile.userImage);
    }

    private void clickListeners() {

        binding.visibilityToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.privateLayout.setOnClickListener(view -> {

            visibility = false;
            visible = 0;

            binding.publicLayout.setBackgroundResource(R.drawable.only_grey_stroke);
            binding.publicSelected.setVisibility(View.GONE);

            binding.privateLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
            binding.privateSelected.setVisibility(View.VISIBLE);
        });

        binding.publicLayout.setOnClickListener(view -> {

            visibility = true;
            visible = 1;


            binding.privateLayout.setBackgroundResource(R.drawable.only_grey_stroke);
            binding.privateSelected.setVisibility(View.GONE);

            binding.publicLayout.setBackgroundResource(R.drawable.selected_gradient_stroke);
            binding.publicSelected.setVisibility(View.VISIBLE);
        });

        binding.btnNext.setOnClickListener(view -> {

            // MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());
            if (!slot.isEmpty()) {
                Constants.SLOTS = slot;

            } else {
                Constants.SLOTS = "4";
            }
//            pm.saveBooleanData(Split.getAppContext(), "VISIBILITY", visibility);
            Constants.VISIBILITY_string = String.valueOf(visible);
            Constants.VISIBILITY = visibility;

            Navigation.findNavController(view).navigate(R.id.action_visibility2_to_cost2);
        });
    }
}