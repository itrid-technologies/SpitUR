package split.com.app.ui.main.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.SecretKeyModel;
import split.com.app.data.model.basic_model.BasicModel;
import split.com.app.data.repository.razor_key.RazorKeyRepository;
import split.com.app.data.repository.user_id.UserIdRepository;

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
