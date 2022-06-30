package split.com.app.ui.main.viewmodel.profile_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.active_user.ActiveUserModel;
import split.com.app.data.model.basic_model.BasicModel;
import split.com.app.data.model.basic_model.BasicModel1;
import split.com.app.data.model.register.RegisterModel;
import split.com.app.data.model.total_coins.TotalCoinsModel;
import split.com.app.data.model.update_user_profile.UserUpdateModel;
import split.com.app.data.repository.profile.ProfileRepository;
import split.com.app.data.repository.register.RegisterRepository;

public class ProfileViewModel extends ViewModel {
    String name , userid, url,id;

    private MutableLiveData<UserUpdateModel> update_profile;
    private MutableLiveData<TotalCoinsModel> coins_data;

    private MutableLiveData<BasicModel1> logout;


    private ProfileRepository profileRepository;
    private MutableLiveData<ActiveUserModel> user_data;

    public ProfileViewModel(String ID, String name, String userid, String avatar) {
        this.id = ID;
        this.name = name;
        this.userid = userid;
        this.url = avatar;
        profileRepository = new ProfileRepository();
    }

    public void init() {
        if (this.update_profile != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        update_profile = profileRepository.upDateProfile(name,userid,url);
    }

    public void initCoins() {
        if (this.coins_data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        coins_data = profileRepository.getTotalCoins(id);
    }

    public void initUser() {
        if (this.user_data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        user_data = profileRepository.getUser();
    }

    public void initLogout() {
        if (this.logout != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        logout = profileRepository.logout(id);
    }


    public MutableLiveData<BasicModel1> getLogout() {
        return logout;
    }

    public MutableLiveData<TotalCoinsModel> getCoins_data() {
        return coins_data;
    }

    public MutableLiveData<UserUpdateModel> getUpdate_profile() {
        return update_profile;
    }

    public MutableLiveData<ActiveUserModel> getUser_data() {
        return user_data;
    }
}
