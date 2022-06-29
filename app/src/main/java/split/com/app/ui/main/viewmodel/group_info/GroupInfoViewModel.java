package split.com.app.ui.main.viewmodel.group_info;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.group_score.GroupScoreModel;
import split.com.app.data.model.join_group.JoinGroupModel;
import split.com.app.data.repository.group_information.GroupInformationRepository;
import split.com.app.data.repository.join_group.JoinGroupRepository;
import split.com.app.ui.main.view.group_info.GroupInformation;

public class GroupInfoViewModel extends ViewModel {


    private MutableLiveData<GroupScoreModel> data;
    private GroupInformationRepository repository;
    String groupId;

    public GroupInfoViewModel(String id) {
        this.groupId = id;
        repository = new GroupInformationRepository();
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = repository.getGroupScore(groupId);
    }

    public MutableLiveData<GroupScoreModel> getData() {
        return this.data;
    }
}
