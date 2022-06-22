package split.com.app.ui.main.viewmodel.group_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.HomeContentModel;
import split.com.app.data.model.group_detail.GroupDetailModel;
import split.com.app.data.repository.group_detail.GroupDetailRepository;
import split.com.app.data.repository.home.HomeContentRepository;

public class GroupDetailViewModel extends ViewModel {

    private MutableLiveData<GroupDetailModel> detailModelMutableLiveData;
    private MutableLiveData<GroupDetailModel> detailSearchData;

    private GroupDetailRepository groupDetailRepository;
    String  subCategoryId, query;

    public GroupDetailViewModel(String id, String data) {
        this.subCategoryId = id;
        this.query = data;
        groupDetailRepository = new GroupDetailRepository();
    }

    public void init() {
        if (this.detailModelMutableLiveData != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        detailModelMutableLiveData = groupDetailRepository.getDetails(subCategoryId);
    }

    public void initSearch() {
        if (this.detailSearchData != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        detailSearchData = groupDetailRepository.getDetailsBySearch(subCategoryId,query);
    }

    public MutableLiveData<GroupDetailModel> getDetailData() {
        return this.detailModelMutableLiveData;
    }

    public MutableLiveData<GroupDetailModel> getDetailSearchData() {
        return this.detailSearchData;
    }




}