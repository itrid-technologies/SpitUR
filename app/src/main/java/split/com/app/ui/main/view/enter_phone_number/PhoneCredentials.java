package split.com.app.ui.main.view.enter_phone_number;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import split.com.app.R;
import split.com.app.databinding.FragmentPhoneCredentialsBinding;
import split.com.app.ui.main.view.full_name.Name;
import split.com.app.ui.main.view.otp_phone_number.OtpNumber;
import split.com.app.ui.main.view.otp_verification.OtpVerification;
import split.com.app.ui.main.viewmodel.phone_number.PhoneNumberViewModel;
import split.com.app.utils.ActivityUtil;
import split.com.app.utils.Constants;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;


public class PhoneCredentials extends Fragment {

    FragmentPhoneCredentialsBinding binding;
    private PhoneNumberViewModel mViewModel;

    String my_number;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPhoneCredentialsBinding.inflate(inflater, container, false);

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
        String user_name = preferences.getData(Split.getAppContext(), "userName");
        String user_ID = preferences.getData(Split.getAppContext(), "userId");
        String avatar = preferences.getData(Split.getAppContext(), "userAvatar");
        String slot = preferences.getData(Split.getAppContext(), "SLOTS");

        binding.phoneProfile.netflix.setText(user_name);
        binding.phoneProfile.userName.setText(user_ID);
        binding.phoneProfile.count.setText(slot + " Slots");
        Glide.with(Split.getAppContext()).load(avatar).placeholder(R.drawable.user).into(binding.phoneProfile.userImage);
    }


    private void initClickListeners() {
        binding.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.sendOtpButton.setOnClickListener(view -> {
            String number = binding.phoneNumber.getText().toString().trim();
            String code = binding.countryCodePicker.getSelectedCountryCode().toString().trim();
            if (!number.isEmpty()) {

                my_number = "+"+code+number;
                Bundle bundle = new Bundle();
                bundle.putString("phone_number",my_number);

              //  Navigation.findNavController(requireView()).navigate(R.id.action_phoneCredentials_to_otpVerifyFragment,bundle);


                mViewModel = new PhoneNumberViewModel(my_number);
                mViewModel.init();

                mViewModel.getData().observe(getViewLifecycleOwner(), numberModel -> {
                    if (numberModel.isStatus()){

                        sentOtp(numberModel.getPhone());

                    }
                });


            }else{
                binding.errorMessage.setText("Enter number");
                binding.errorMessage.setVisibility(View.VISIBLE);
            }
        });
    }

    private void sentOtp(String phone) {
        mViewModel.initOtp();
        mViewModel.getOtp_data().observe(getViewLifecycleOwner(), otpModel -> {
            if (otpModel.isStatus()){

                Bundle bundle = new Bundle();
                bundle.putString("phone_number",phone);
                Constants.NUMBER = phone;
                Navigation.findNavController(requireView()).navigate(R.id.action_phoneCredentials_to_otpVerifyFragment,bundle);

            }else {
                binding.errorMessage.setVisibility(View.VISIBLE);
                binding.errorMessage.setText(otpModel.getMessage());
            }
        });
    }
}