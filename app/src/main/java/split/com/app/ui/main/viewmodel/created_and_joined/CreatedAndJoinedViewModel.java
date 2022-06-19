package split.com.app.ui.main.viewmodel.created_and_joined;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import split.com.app.data.model.all_created_groupx.AllCreatedGroupModel;
import split.com.app.data.model.create_group.CreateGroupModel;
import split.com.app.data.model.group_detail.GroupDetailModel;
import split.com.app.data.repository.create_group.CreateGroupRepository;
import split.com.app.data.repository.created_and_joined.CreatedAndJoinedGroupRepository;
import split.com.app.ui.main.adapter.all_joined_group.AllJoinedGroupAdapter;

public class CreatedAndJoinedViewModel extends ViewModel {

    private MutableLiveData<AllCreatedGroupModel> data;
    private MutableLiveData<GroupDetailModel> join_data;

    private CreatedAndJoinedGroupRepository repository;

    public CreatedAndJoinedViewModel() {
        repository = new CreatedAndJoinedGroupRepository();
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = repository.getAllCreatedGroups();
    }

    public void initJoin() {
        if (this.join_data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        join_data = repository.getAllJoinedGroups();
    }

    public MutableLiveData<AllCreatedGroupModel> getData() {
        return this.data;
    }

    public MutableLiveData<GroupDetailModel> getJoinData() {
        return this.join_data;
    }

}
