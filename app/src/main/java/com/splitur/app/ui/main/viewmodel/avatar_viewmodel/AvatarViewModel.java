package com.splitur.app.ui.main.viewmodel.avatar_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.get_avatar.AvatarModel;
import com.splitur.app.data.repository.avatar.AvatarRepository;

public class AvatarViewModel extends ViewModel {

    private MutableLiveData<AvatarModel> data;
    private AvatarRepository avatarRepository;

    public AvatarViewModel() {
        avatarRepository = new AvatarRepository();
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = avatarRepository.getAvatar();
    }

    public MutableLiveData<AvatarModel> getData() {
        return this.data;
    }

}
