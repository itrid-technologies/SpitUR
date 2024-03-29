package com.splitur.app.ui.main.viewmodel.user_id;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.basic_model.BasicModel;
import com.splitur.app.data.repository.user_id.UserIdRepository;

public class UserIdViewModel extends ViewModel {


    String id;

    private MutableLiveData<BasicModel> data;
    private final UserIdRepository userIdRepository;

    public UserIdViewModel(String userid) {
        this.id = userid;
        userIdRepository = new UserIdRepository();
    }

    public void init() {
        if (this.data != null) {
            return;
        }
        data = userIdRepository.checkUserId(id);
    }

    public MutableLiveData<BasicModel> getData() {
        return this.data;
    }

}
