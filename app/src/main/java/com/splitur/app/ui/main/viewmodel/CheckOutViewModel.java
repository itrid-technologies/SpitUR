package com.splitur.app.ui.main.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.SecretKeyModel;
import com.splitur.app.data.repository.razor_key.RazorKeyRepository;

public class CheckOutViewModel extends ViewModel {

    private MutableLiveData<SecretKeyModel> data;
    private final RazorKeyRepository keyRepository;

    public CheckOutViewModel() {
        keyRepository = new RazorKeyRepository();
    }

    public void init() {
        if (this.data != null) {
            return;
        }
        data = keyRepository.getKey();
    }

    public MutableLiveData<SecretKeyModel> getData() {
        return this.data;
    }

}
