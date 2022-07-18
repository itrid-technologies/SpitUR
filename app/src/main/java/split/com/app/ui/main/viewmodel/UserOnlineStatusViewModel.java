package split.com.app.ui.main.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.SecretKeyModel;
import split.com.app.data.model.basic_model.BasicModel;
import split.com.app.data.model.basic_model.BasicModel1;
import split.com.app.data.repository.ONLINE_OFFLINE.UserOnlineStatusRepository;
import split.com.app.data.repository.razor_key.RazorKeyRepository;

public class UserOnlineStatusViewModel extends ViewModel {

    private MutableLiveData<BasicModel1> data;
    private final UserOnlineStatusRepository onlineStatusRepository;
    int status;

    public UserOnlineStatusViewModel(int data) {
        this.status = data;
        onlineStatusRepository = new UserOnlineStatusRepository();
    }

    public void init() {
        if (this.data != null) {
            return;
        }
        data = onlineStatusRepository.userStatus(status);
    }

    public MutableLiveData<BasicModel1> getData() {
        return this.data;
    }
}
