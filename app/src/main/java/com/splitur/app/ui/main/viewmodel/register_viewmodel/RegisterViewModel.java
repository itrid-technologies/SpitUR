package com.splitur.app.ui.main.viewmodel.register_viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.register.RegisterModel;
import com.splitur.app.data.repository.register.RegisterRepository;

public class RegisterViewModel extends ViewModel {


    String name, number , id, url;

    private MutableLiveData<RegisterModel> data;
    private RegisterRepository registerRepository;

    public RegisterViewModel(String name, String number, String userid, String avatar) {
        this.name = name;
        this.number = number;
        this.id = userid;
        this.url = avatar;
        registerRepository = new RegisterRepository();
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = registerRepository.registerUser(number,name,id,url);
    }

    public MutableLiveData<RegisterModel> getData() {
        return this.data;
    }


}
