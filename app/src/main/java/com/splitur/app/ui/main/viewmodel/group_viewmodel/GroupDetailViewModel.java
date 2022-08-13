package com.splitur.app.ui.main.viewmodel.group_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.group_detail.GroupDetailModel;
import com.splitur.app.data.repository.group_detail.GroupDetailRepository;

public class GroupDetailViewModel extends ViewModel {

    private MutableLiveData<GroupDetailModel> detailModelMutableLiveData;
    private MutableLiveData<GroupDetailModel> detailSearchData;
    private MutableLiveData<GroupDetailModel> detailSearchDataByGroupId;
    private MutableLiveData<GroupDetailModel> detailSearchDataByUserId;

    private GroupDetailRepository groupDetailRepository;
    String subCategoryId, query, pageNo;

    public GroupDetailViewModel(String id, String data, String page) {
        this.subCategoryId = id;
        this.query = data;
        this.pageNo = page;
        groupDetailRepository = new GroupDetailRepository();
    }

    public void init() {
        if (this.detailModelMutableLiveData != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        detailModelMutableLiveData = groupDetailRepository.getDetails(subCategoryId, pageNo);//query is page no
    }

    public void initSearch() {
        if (this.detailSearchData != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        detailSearchData = groupDetailRepository.getDetailsBySearch(subCategoryId, query, Integer.parseInt(pageNo));
    }


    public void initSearchByGroupId() {
        if (this.detailSearchDataByGroupId != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        detailSearchDataByGroupId = groupDetailRepository.getDetailsBySearchGroupId(subCategoryId, query, Integer.parseInt(pageNo));
    }


    public void initSearchByUserId() {
        if (this.detailSearchDataByUserId != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        detailSearchDataByUserId = groupDetailRepository.getDetailsBySearchUserId(subCategoryId, query, Integer.parseInt(pageNo));
    }

    public MutableLiveData<GroupDetailModel> getDetailData() {
        return this.detailModelMutableLiveData;
    }

    public MutableLiveData<GroupDetailModel> getDetailSearchData() {
        return this.detailSearchData;
    }

    public MutableLiveData<GroupDetailModel> getDetailSearchDataByGroupId() {
        return detailSearchDataByGroupId;
    }

    public MutableLiveData<GroupDetailModel> getDetailSearchDataByUserId() {
        return detailSearchDataByUserId;
    }
}