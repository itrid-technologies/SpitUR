package split.com.app.ui.main.view.otp_verify_fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import split.com.app.R;
import split.com.app.data.repository.create_group.CreateGroupRepository;
import split.com.app.databinding.FragmentOtpVerifyBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.view.otp_verification.OtpVerification;
import split.com.app.ui.main.viewmodel.craete_group_viewModel.CreateGroupViewModel;
import split.com.app.ui.main.viewmodel.otp_verification_viewmodel.OtpVerificationViewModel;
import split.com.app.ui.main.viewmodel.search_create_viewmodel.SearchCreateViewModel;
import split.com.app.utils.ActivityUtil;
import split.com.app.utils.Constants;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;


public class OtpVerifyFragment extends Fragment {

    FragmentOtpVerifyBinding binding;
    String number;
    private OtpVerificationViewModel mViewModel;
    private CreateGroupViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOtpVerifyBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.toolbar.title.setText("Verify Phone Number");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null){
            number = getArguments().getString("phone_number");
        }

        initClickListeners();
    }

    private void initClickListeners() {

        binding.toolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.verifyButton.setOnClickListener(view -> {
            String otp = binding.otp.getText().toString();

            if (!otp.isEmpty()) {
                if (otp.length() == 4) {

                    authenticateUser(number, otp);


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

    private void authenticateUser(String number, String otp) {
        mViewModel = new OtpVerificationViewModel(number, otp);
        mViewModel.init();
        mViewModel.getData().observe(getViewLifecycleOwner(), authenticationModel -> {
            if (authenticationModel.isSuccess()) {

                MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());

                pm.saveData(Split.getAppContext(), "userAccessToken", authenticationModel.getData().getToken());

                String planId = pm.getData(Split.getAppContext(),"PLAN_ID");
                String slots = pm.getData(Split.getAppContext(),"SLOTS");
                String cost = pm.getData(Split.getAppContext(),"COST");
                String email = pm.getData(Split.getAppContext(),"EMAIL");
                String password = pm.getData(Split.getAppContext(),"PASSWORD");
                String visibility = pm.getData(Split.getAppContext(),"VISIBILITY");
                String title = pm.getData(Split.getAppContext(),"TITLE");


                viewModel = new CreateGroupViewModel(planId,title,slots,cost,email,password,visibility);
                viewModel.init();
                viewModel.getData().observe(getViewLifecycleOwner(), createGroupModel -> {
                    if (createGroupModel.isSuccess()){
                        if(createGroupModel.getData() != null) {
                          //  MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());
                            pm.saveData(Split.getAppContext(), "PLAN_ID", String.valueOf(createGroupModel.getData().getPlansId()));
                            pm.saveData(Split.getAppContext(), "SLOTS", String.valueOf(createGroupModel.getData().getSlots()));
                            pm.saveData(Split.getAppContext(), "COST", String.valueOf(createGroupModel.getData().getCostPerMember()));
                            pm.saveData(Split.getAppContext(), "EMAIL", String.valueOf(createGroupModel.getData().getEmail()));
                            pm.saveData(Split.getAppContext(), "PASSWORD", String.valueOf(createGroupModel.getData().getPassword()));
                            pm.saveData(Split.getAppContext(), "VISIBILITY", String.valueOf(createGroupModel.getData().isVisibility()));
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