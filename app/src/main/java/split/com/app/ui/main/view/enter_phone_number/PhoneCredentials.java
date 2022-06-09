package split.com.app.ui.main.view.enter_phone_number;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import split.com.app.R;
import split.com.app.databinding.FragmentPhoneCredentialsBinding;
import split.com.app.ui.main.view.full_name.Name;
import split.com.app.ui.main.view.otp_phone_number.OtpNumber;
import split.com.app.ui.main.view.otp_verification.OtpVerification;
import split.com.app.ui.main.viewmodel.phone_number.PhoneNumberViewModel;
import split.com.app.utils.ActivityUtil;
import split.com.app.utils.MySharedPreferences;


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

                Navigation.findNavController(requireView()).navigate(R.id.action_phoneCredentials_to_otpVerifyFragment,bundle);

            }else {
                binding.errorMessage.setVisibility(View.VISIBLE);
                binding.errorMessage.setText(otpModel.getMessage());
            }
        });
    }
}