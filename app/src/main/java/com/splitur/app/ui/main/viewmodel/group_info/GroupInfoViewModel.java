package com.splitur.app.ui.main.viewmodel.group_info;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.group_score.GroupScoreModel;
import com.splitur.app.data.repository.group_information.GroupInformationRepository;

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
