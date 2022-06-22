package split.com.app.ui.main.viewmodel.profile_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.register.RegisterModel;
import split.com.app.data.model.update_user_profile.UserUpdateModel;
import split.com.app.data.repository.profile.ProfileRepository;
import split.com.app.data.repository.register.RegisterRepository;

public class ProfileViewModel extends ViewModel {
    String name , id, url;

    private MutableLiveData<UserUpdateModel> update_profile;
    private ProfileRepository profileRepository;

    public ProfileViewModel(String name, String userid, String avatar) {
        this.name = name;
        this.id = userid;
        this.url = avatar;
        profileRepository = new ProfileRepository();
    }

    public void init() {
        if (this.update_profile != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        update_profile = profileRepository.upDateProfile(name,id,url);
    }


    public MutableLiveData<UserUpdateModel> getUpdate_profile() {
        return update_profile;
    }
}
