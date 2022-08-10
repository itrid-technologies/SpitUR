package com.splitur.app.ui.main.viewmodel.otp_verification_viewmodel;

import android.app.Activity;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.otp_verification.AuthenticationModel;
import com.splitur.app.data.repository.otp_verification.OtpVerificationRepository;
import com.splitur.app.ui.main.view.otp_verification.OtpVerification;

public class OtpVerificationViewModel extends ViewModel {

    public ObservableField<String> otp = new ObservableField<>("");
    String otp_number, phone_number;

    private MutableLiveData<AuthenticationModel> data;
    private OtpVerificationRepository verificationRepository;
    Activity activity;

    public OtpVerificationViewModel(String number, String otp, Activity otpScreen) {
        this.phone_number = number;
        this.otp_number = otp;
        this.activity = otpScreen;
        verificationRepository = new OtpVerificationRepository();
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = verificationRepository.verifyUser(phone_number,otp_number,activity);
    }

    public MutableLiveData<AuthenticationModel> getData() {
        return this.data;
    }

}
