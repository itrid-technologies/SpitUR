package split.com.app.ui.main.viewmodel.memebers_viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.prefs.BackingStoreException;

import split.com.app.data.model.basic_model.BasicModel;
import split.com.app.data.model.group_member.GroupMemberModel;
import split.com.app.data.model.plans.PlanModel;
import split.com.app.data.repository.delete_group.DeleteGroupRepository;
import split.com.app.data.repository.get_member.GroupMembersRepository;
import split.com.app.data.repository.plans.PlanRepository;

public class GroupMembersViewModel extends ViewModel {

    String group_id;

    private MutableLiveData<GroupMemberModel> data;
    private MutableLiveData<BasicModel> remove_data;

    private GroupMembersRepository membersRepository;
    private DeleteGroupRepository deleteGroupRepository;


    public GroupMembersViewModel(String id) {
        this.group_id = id;
        membersRepository = new GroupMembersRepository();
        deleteGroupRepository = new DeleteGroupRepository();
    }



    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = membersRepository.getMembers(group_id);
    }

    public void initRemoveGroup() {
        if (this.remove_data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        remove_data = deleteGroupRepository.delete(group_id);
    }


    public MutableLiveData<GroupMemberModel> getPlan() {
        return this.data;
    }

    public MutableLiveData<BasicModel> getRemove_data() {
        return this.remove_data;
    }


}
