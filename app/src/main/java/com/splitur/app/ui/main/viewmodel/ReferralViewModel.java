package com.splitur.app.ui.main.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.basic_model.BasicModel1;
import com.splitur.app.data.repository.referrel.ReferralRepository;

public class ReferralViewModel extends ViewModel {
    private MutableLiveData<BasicModel1> data;
    private final ReferralRepository referralRepository;
    String refer_by , referral;

    public ReferralViewModel(String ID, String referrer) {
        this.refer_by = ID;
        this.referral = referrer;
        referralRepository = new ReferralRepository();
    }

    public void init() {
        if (this.data != null) {
            return;
        }
        data = referralRepository.sendReferral(refer_by,referral);
    }

    public MutableLiveData<BasicModel1> getData() {
        return this.data;
    }
}
