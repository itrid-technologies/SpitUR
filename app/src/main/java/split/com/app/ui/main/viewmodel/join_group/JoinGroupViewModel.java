package split.com.app.ui.main.viewmodel.join_group;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.join_group.JoinGroupModel;
import split.com.app.data.repository.join_group.JoinGroupRepository;

public class JoinGroupViewModel extends ViewModel {

    private MutableLiveData<JoinGroupModel> data;
    private JoinGroupRepository joinGroupRepository;
    String groupId , upiId, email;

    public JoinGroupViewModel(String id, String email, String upi_id) {
        this.groupId = id;
        this.email = email;
        this.upiId = upi_id;
        joinGroupRepository = new JoinGroupRepository();
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = joinGroupRepository.join(groupId,email,upiId);
    }

    public MutableLiveData<JoinGroupModel> getData() {
        return this.data;
    }

}
