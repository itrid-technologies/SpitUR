package split.com.app.ui.main.viewmodel.memebers_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.basic_model.BasicModel;
import split.com.app.data.model.basic_model.BasicModel1;
import split.com.app.data.model.group_member.GroupMemberModel;
import split.com.app.data.repository.delete_group.DeleteGroupRepository;
import split.com.app.data.repository.get_member.GroupMembersRepository;
import split.com.app.data.repository.update_group.UpdateGroupRepository;

public class GroupMembersViewModel extends ViewModel {

    String group_id, user_id, email, password;
    boolean visible;

    private MutableLiveData<GroupMemberModel> data;
    private MutableLiveData<BasicModel1> remove_data;
    private MutableLiveData<BasicModel1> member_remove_data;
    private MutableLiveData<BasicModel1> update_email_data;
    private MutableLiveData<BasicModel1> update_pass_data;
    private MutableLiveData<BasicModel> update_visibility;
    private MutableLiveData<BasicModel1> left_data;


    private GroupMembersRepository membersRepository;
    private DeleteGroupRepository deleteGroupRepository;
    private UpdateGroupRepository updateGroupRepository;


    public GroupMembersViewModel(String id, String userid, String email, String Pass, boolean visibility) {
        this.group_id = id;
        this.user_id = userid;
        this.email = email;
        this.password = Pass;
        this.visible = visibility;
        membersRepository = new GroupMembersRepository();
        deleteGroupRepository = new DeleteGroupRepository();
        updateGroupRepository = new UpdateGroupRepository();
    }


    public void init() {
        if (this.data != null) {
            return;
        }
        data = membersRepository.getMembers(group_id);
    }

    public void initRemoveGroup() {
        if (this.remove_data != null) {
            return;
        }
        remove_data = deleteGroupRepository.delete(group_id);
    }

    public void initLeftGroup() {
        if (this.left_data != null) {
            return;
        }
        left_data = deleteGroupRepository.left(group_id);
    }

    public void initRemoveMember() {
        if (this.member_remove_data != null) {

            return;
        }
        member_remove_data = membersRepository.removeGroupMember(group_id, user_id);
    }

    public void initEmailUpdate() {
        if (this.update_email_data != null) {
            // ViewModel is _gorucreated per Fragment so
            // we know the userId won't change
            return;
        }
        update_email_data = updateGroupRepository.updateEmail(group_id, email);
    }

    public void initPassUpdate() {
        if (this.update_pass_data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        update_pass_data = updateGroupRepository.updatePass(group_id, password);
    }

    public void initUpdateVisibility() {
        if (this.update_visibility != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        update_visibility = updateGroupRepository.updateVisibility(group_id,visible);
    }



    public MutableLiveData<GroupMemberModel> getPlan() {
        return this.data;
    }

    public MutableLiveData<BasicModel1> getRemove_data() {
        return this.remove_data;
    }

    public MutableLiveData<BasicModel1> getMember_remove_data() {
        return this.member_remove_data;
    }

    public MutableLiveData<BasicModel1> getUpdate_email_data() {
        return this.update_email_data;
    }
    public MutableLiveData<BasicModel1> getUpdate_pass_data() {
        return this.update_pass_data;
    }
    public MutableLiveData<BasicModel> getUpdate_visibility() {
        return this.update_visibility;
    }

    public MutableLiveData<BasicModel1> getLeft_data() {
        return left_data;
    }
}
