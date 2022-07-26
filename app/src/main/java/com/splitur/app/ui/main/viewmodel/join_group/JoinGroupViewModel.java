package com.splitur.app.ui.main.viewmodel.join_group;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.join_group.JoinGroupModel;
import com.splitur.app.data.repository.join_group.JoinGroupRepository;

public class JoinGroupViewModel extends ViewModel {

    private MutableLiveData<JoinGroupModel> data;
    private JoinGroupRepository joinGroupRepository;
    String groupId, upiId, email, paymentId, promoCode;

    public JoinGroupViewModel(String id, String email, String code) {
        this.groupId = id;
        this.email = email;
        this.promoCode = code;
        joinGroupRepository = new JoinGroupRepository();
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = joinGroupRepository.join(groupId, email, promoCode);
    }

    public MutableLiveData<JoinGroupModel> getData() {
        return this.data;
    }

}
