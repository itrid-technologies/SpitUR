package split.com.app.ui.main.viewmodel.join_checkout_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.basic_model.BasicModel;
import split.com.app.data.model.join_group.JoinGroupModel;
import split.com.app.data.repository.JoinCheckoutRepository;
import split.com.app.data.repository.join_group.JoinGroupRepository;

public class JoinCheckoutViewModel extends ViewModel {


    private MutableLiveData<BasicModel> data;
    private JoinCheckoutRepository joinCheckoutRepository;
    String user_id;

    public JoinCheckoutViewModel(String id) {
        this.user_id = id;
        joinCheckoutRepository = new JoinCheckoutRepository();
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = joinCheckoutRepository.checkUserEmail(user_id);
    }

    public MutableLiveData<BasicModel> getData() {
        return this.data;
    }
}
