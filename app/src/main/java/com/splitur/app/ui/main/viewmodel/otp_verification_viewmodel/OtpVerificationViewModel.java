package com.splitur.app.ui.main.viewmodel.otp_verification_viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.otp_verification.AuthenticationModel;
import com.splitur.app.data.repository.otp_verification.OtpVerificationRepository;

public class OtpVerificationViewModel extends ViewModel {

    public ObservableField<String> otp = new ObservableField<>("");
    String otp_number, phone_number;

    private MutableLiveData<AuthenticationModel> data;
    private OtpVerificationRepository verificationRepository;

    public OtpVerificationViewModel(String number, String otp) {
        this.phone_number = number;
        this.otp_number = otp;
        verificationRepository = new OtpVerificationRepository();
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = verificationRepository.verifyUser(phone_number,otp_number);
    }

    public MutableLiveData<AuthenticationModel> getData() {
        return this.data;
    }

}
