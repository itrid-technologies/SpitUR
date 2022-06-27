package split.com.app.ui.main.viewmodel.custom_create_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.basic_model.BasicModel;
import split.com.app.data.model.create_group.CreateGroupModel;
import split.com.app.data.repository.create_group.CreateGroupRepository;
import split.com.app.data.repository.custom_create.CustomCreateRepository;

public class CustomCreateViewModel extends ViewModel {

    String title,type, slot , cost , email , password, visibility , subCatTtile , phone;

    private MutableLiveData<BasicModel> data;
    private CustomCreateRepository customCreateRepository;

    public CustomCreateViewModel(String title, String slots, String cost, String email, String pass, String visible, String sub_catTitle, String type, String number) {
        this.title = title;
        this.slot = slots;
        this.cost = cost;
        this.email = email;
        this.password = pass;
        this.visibility = visible;
        this.subCatTtile = sub_catTitle;
        this.type = type;
        this.phone = number;
        customCreateRepository = new CustomCreateRepository();
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = customCreateRepository.create(title,slot,cost,email,password,visibility,subCatTtile,type);
    }

    public MutableLiveData<BasicModel> getData() {
        return this.data;
    }
}
