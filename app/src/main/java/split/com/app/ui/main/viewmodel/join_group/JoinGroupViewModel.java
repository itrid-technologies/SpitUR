package split.com.app.ui.main.viewmodel.join_group;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.HomeContentModel;
import split.com.app.data.model.basic_model.BasicModel;
import split.com.app.data.repository.home.HomeContentRepository;
import split.com.app.data.repository.join_group.JoinGroupRepository;

public class JoinGroupViewModel extends ViewModel {

    private MutableLiveData<BasicModel> data;
    private JoinGroupRepository joinGroupRepository;
    String groupId;

    public JoinGroupViewModel(String id) {
        this.groupId = id;
        joinGroupRepository = new JoinGroupRepository();
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = joinGroupRepository.join(groupId);
    }

    public MutableLiveData<BasicModel> getData() {
        return this.data;
    }

}
