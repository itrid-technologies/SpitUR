package com.splitur.app.ui.main.viewmodel.otp_request_viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.splitur.app.data.model.basic_model.BasicModel;
import com.splitur.app.data.repository.request_otp.OtpRequestRepository;

public class OtpRequestViewModel {

    String id;

    private MutableLiveData<BasicModel> data;
    private OtpRequestRepository otpRequestRepository;

    public OtpRequestViewModel(String groupId) {
        this.id = groupId;
        otpRequestRepository = new OtpRequestRepository();}

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = otpRequestRepository.request(id);
    }

    public MutableLiveData<BasicModel> getData() {
        return this.data;
    }
}
