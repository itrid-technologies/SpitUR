package split.com.app.ui.main.viewmodel.craete_group_viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.create_group.CreateGroupModel;
import split.com.app.data.model.register.RegisterModel;
import split.com.app.data.repository.create_group.CreateGroupRepository;
import split.com.app.data.repository.register.RegisterRepository;

public class CreateGroupViewModel extends ViewModel {

    String plan_id,name, slot , cost , email , password, visibility , id;

    private MutableLiveData<CreateGroupModel> data;
    private CreateGroupRepository groupRepository;

    public CreateGroupViewModel(String planId,String title, String slots, String cost, String email, String pass, String visible, String sub_catId) {
        this.plan_id = planId;
        this.name = title;
        this.slot = slots;
        this.cost = cost;
        this.email = email;
        this.password = pass;
        this.visibility = visible;
        this.id = sub_catId;

        groupRepository = new CreateGroupRepository();
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = groupRepository.createGroup(plan_id,name,slot,cost,email,password,visibility,id);
    }

    public MutableLiveData<CreateGroupModel> getData() {
        return this.data;
    }
}
