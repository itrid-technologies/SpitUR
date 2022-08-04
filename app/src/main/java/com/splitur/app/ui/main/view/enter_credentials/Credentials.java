package com.splitur.app.ui.main.view.enter_credentials;

import android.os.Bundle;
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
import com.splitur.app.databinding.FragmentCredentialsBinding;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.viewmodel.craete_group_viewModel.CreateGroupViewModel;
import com.splitur.app.ui.main.viewmodel.custom_create_viewmodel.CustomCreateViewModel;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.MySharedPreferences;
import com.splitur.app.utils.Split;


public class Credentials extends Fragment {


    FragmentCredentialsBinding binding;
    private CustomCreateViewModel viewModel;
    private CreateGroupViewModel createGroupViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCredentialsBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.credentialsToolbar.title.setText("Credentials");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initClickListeners();
        setProfileData();

    }

    private void setProfileData() {
        String user_name = Constants.USER_NAME;
        String user_ID = Constants.USER_ID;
        String avatar = Constants.USER_AVATAR;
        String slot = Constants.SLOTS;

        binding.credentialProfile.netflix.setText(Constants.SUB_CAT_TITLE);
        binding.credentialProfile.userName.setText(String.format("@%s", user_ID));
        binding.credentialProfile.count.setText(slot + " Slots");
        binding.credentialProfile.userImage.setImageResource(Constants.getAvatarIcon(requireContext(), Integer.parseInt(avatar)));
    }

    private void initClickListeners() {
        binding.credentialsToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.btnNext.setOnClickListener(view -> {
            String email = binding.edUsername.getText().toString().trim();
            String password = binding.edPassword.getText().toString().trim();


            if (email.isEmpty()) {
                binding.errorMessage.setText("Enter email");
                binding.errorMessage.setVisibility(View.VISIBLE);
//            }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//                binding.errorMessage.setText("Enter valid email");
////                binding.errorMessage.setVisibility(View.VISIBLE);
//            } else if (password.isEmpty()) {
//                binding.errorMessage.setText("Enter password");
//                binding.errorMessage.setVisibility(View.VISIBLE);
            } else {
                binding.errorMessage.setVisibility(View.GONE);

                Constants.EMAIL = email;
                Constants.PASSWORD = password;
                String validation_type = Constants.VALIDATION_TYPE;
                if (!validation_type.isEmpty()) {
                    hitCustomCreateApi();
                } else {

                    String planId = Constants.PLAN_ID;
                    String slots = Constants.SLOTS;
                    String cost = Constants.COST;
                    String email1 = Constants.EMAIL;
                    String password1 = Constants.PASSWORD;
                    String visibility = Constants.VISIBILITY_string;
                    String title = Constants.SUB_CAT_TITLE;
                    String sub_catId = Constants.SUB_CATEGORY_ID;


                    createGroupViewModel = new CreateGroupViewModel(planId, title, slots, cost, email1, password1, visibility, sub_catId);
                    createGroupViewModel.init();
                    createGroupViewModel.getData().observe(getViewLifecycleOwner(), createGroupModel -> {
                        if (createGroupModel.isSuccess()) {
                            Toast.makeText(requireContext(), createGroupModel.getMessage(), Toast.LENGTH_SHORT).show();

                            if (createGroupModel.getData() != null) {

                                MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());


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

                                Navigation.findNavController(requireView()).navigate(R.id.action_credentials2_to_otpSuccess);
                            }
                        } else {
                            Toast.makeText(requireContext(), createGroupModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
//                    Navigation.findNavController(view).navigate(R.id.action_credentials2_to_phoneCredentials);
                    // Navigation.findNavController(requireView()).navigate(R.id.action_credentials2_to_otpSuccess);
                }
            }

        });
    }

    private void hitCustomCreateApi() {

        String title = Constants.GROUP_TITLE;
        String slots = Constants.SLOTS;
        String cost = Constants.COST;
        String email = Constants.EMAIL;
        String password = Constants.PASSWORD;
        String visibility = Constants.VISIBILITY_string;
        String sub_cat_title = Constants.SUB_CAT_TITLE;
        String type = Constants.VALIDATION_TYPE;
        String number = Constants.NUMBER;

        viewModel = new CustomCreateViewModel(title, slots, cost, email, password, visibility, sub_cat_title, type, number);
        viewModel.initAuth();
        viewModel.getData().observe(getViewLifecycleOwner(), createGroupModel -> {
            if (createGroupModel.getMessage().equalsIgnoreCase("Custom Group created successfully")) {
                Navigation.findNavController(requireView()).navigate(R.id.action_credentials2_to_otpSuccess);
            }
        });
    }
}