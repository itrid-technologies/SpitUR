package com.splitur.app.ui.main.view.otp_verify_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.splitur.app.R;
import com.splitur.app.databinding.FragmentOtpVerifyBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.viewmodel.craete_group_viewModel.CreateGroupViewModel;
import com.splitur.app.ui.main.viewmodel.custom_create_viewmodel.CustomCreateViewModel;
import com.splitur.app.ui.main.viewmodel.otp_verification_viewmodel.OtpVerificationViewModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;


public class OtpVerifyFragment extends Fragment {

    FragmentOtpVerifyBinding binding;
    String number;
    private OtpVerificationViewModel mViewModel;
    private CreateGroupViewModel viewModel;
    CustomCreateViewModel customCreateViewModel;
    MySharedPreferences pm;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOtpVerifyBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.verifyToolbar.title.setText("Verify Phone Number");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            number = getArguments().getString("phone_number");
        }
        pm = new MySharedPreferences(Split.getAppContext());


        initClickListeners();
    }

    private void initClickListeners() {

        binding.verifyToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.verifyButton.setOnClickListener(view -> {
            String otp = binding.otp.getText().toString();

            if (!otp.isEmpty()) {
                if (otp.length() == 4) {

                    String validation_type = Constants.VALIDATION_TYPE;
                    if (!validation_type.isEmpty()) {
                        createCustomGroupByOtp();
                    } else {
                        authenticateUser(number, otp);
                    }


                } else {
                    binding.errorMessage.setText("Enter Complete Digits");
                    binding.errorMessage.setVisibility(View.VISIBLE);
                }

            } else {
                binding.errorMessage.setText("Enter OTP");
                binding.errorMessage.setVisibility(View.VISIBLE);
            }
        });
    }

    private void createCustomGroupByOtp() {
        String title = Constants.GROUP_TITLE;
        String slots = Constants.SLOTS;
        String cost = Constants.COST;
        String email = Constants.EMAIL;
        String password = Constants.PASSWORD;
        String visibility = Constants.VISIBILITY_string;
        String sub_cat_title = Constants.SUB_CAT_TITLE;
        String type = Constants.VALIDATION_TYPE;
        String number = Constants.NUMBER;

        customCreateViewModel = new CustomCreateViewModel(title, slots, cost, email, password, visibility, sub_cat_title, type, number);
        customCreateViewModel.initOtp();
        customCreateViewModel.getOtp_data().observe(getViewLifecycleOwner(), createGroupModel -> {
            if (createGroupModel.getMessage().equalsIgnoreCase("Custom Group created successfully")) {
                Navigation.findNavController(requireView()).navigate(R.id.action_otpVerifyFragment_to_otpSuccess);
            }
        });
    }

    private void authenticateUser(String number, String otp) {
        mViewModel = new OtpVerificationViewModel(number, otp);
        mViewModel.init();
        mViewModel.getData().observe(getViewLifecycleOwner(), authenticationModel -> {
            if (authenticationModel.isSuccess()) {


                pm.saveData(Split.getAppContext(), "userAccessToken", authenticationModel.getData().getToken());

                String planId = Constants.PLAN_ID;
                String slots = Constants.SLOTS;
                String cost = Constants.COST;
                String email = Constants.EMAIL;
                String password = Constants.PASSWORD;
                String visibility = Constants.VISIBILITY_string;
                String title = Constants.SUB_CAT_TITLE;
                String sub_catId = Constants.SUB_CATEGORY_ID;


                viewModel = new CreateGroupViewModel(planId, title, slots, cost, email, password, visibility, sub_catId);
                viewModel.init();
                viewModel.getData().observe(getViewLifecycleOwner(), createGroupModel -> {
                    if (createGroupModel.isSuccess()) {
                        if (createGroupModel.getData() != null) {
                            Constants.PLAN_ID = String.valueOf(createGroupModel.getData().getPlansId());
                            Constants.SLOTS = String.valueOf(createGroupModel.getData().getSlots());
                            Constants.COST = String.valueOf(createGroupModel.getData().getCostPerMember());
                            Constants.EMAIL = String.valueOf(createGroupModel.getData().getEmail());
                            Constants.PASSWORD = String.valueOf(createGroupModel.getData().getPassword());
                            Constants.VISIBILITY = createGroupModel.getData().isVisibility();
                            pm.saveData(Split.getAppContext(), "TITLE", String.valueOf(createGroupModel.getData().getTitle()));
                            pm.saveData(Split.getAppContext(), "ID", String.valueOf(createGroupModel.getData().getId()));
                            pm.saveData(Split.getAppContext(), "GROUP_ID", String.valueOf(createGroupModel.getData().getGroupId()));
                            pm.saveData(Split.getAppContext(), "CREATED_AT", String.valueOf(createGroupModel.getData().getCreatedAt()));

                            Navigation.findNavController(requireView()).navigate(R.id.action_otpVerifyFragment_to_otpSuccess);
                        }
                    }
                });
            }
        });
    }
}